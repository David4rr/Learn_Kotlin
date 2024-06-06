package com.davidarrozaqi.storyapp.ui.add

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.davidarrozaqi.storyapp.R
import com.davidarrozaqi.storyapp.base.BaseFragment
import com.davidarrozaqi.storyapp.data.dto.network.ApiResponse
import com.davidarrozaqi.storyapp.databinding.FragmentAddBinding
import com.davidarrozaqi.storyapp.utils.getImageUri
import com.davidarrozaqi.storyapp.utils.showToast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.yalantis.ucrop.UCrop
import org.koin.android.ext.android.inject
import java.io.File
import java.util.Date

class AddFragment : BaseFragment<FragmentAddBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentAddBinding {
        return FragmentAddBinding.inflate(inflater, container, false)
    }

    private val viewModel: AddViewModel by inject()
    private var lat: Double = 0.0
    private var long: Double = 0.0
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var currentUri: Uri? = null
    private val launchGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentUri = uri
            withUCrop(currentUri!!)
        }
    }

    private val launchUCrop =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val resultUri = UCrop.getOutput(result.data!!)
                if (resultUri != null) {
                    currentUri = resultUri
                    setPreview()
                }
            }
        }

    private val launchCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            withUCrop(currentUri!!)
        }
    }

    private fun withUCrop(uri: Uri) {
        val timestamp = Date().time
        val cachedImage = File(requireActivity().cacheDir, "cropped_image_${timestamp}.jpg")
        val destinationUri = Uri.fromFile(cachedImage)
        val uCrop = UCrop.of(uri, destinationUri).withAspectRatio(1f, 1f)

        uCrop.getIntent(requireContext()).apply {
            launchUCrop.launch(this)
        }
    }

    private fun setPreview() {
        Glide.with(requireContext())
            .load(currentUri)
            .centerCrop()
            .transform(RoundedCorners(32))
            .into(binding.ivStoryImage)
    }

    override fun doSomething() {
        super.doSomething()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        initListener()
        initObserver()
    }

    private fun initListener() {

        with(binding) {
            topBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            cameraButton.setOnClickListener {
                currentUri = getImageUri(requireActivity())
                launchCamera.launch(currentUri)
            }
            galleryButton.setOnClickListener {
                launchGallery.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            }
            btnPost.setOnClickListener {
                val description = edtAddDescription.text.toString()

                if (description.isNotEmpty() && currentUri != null) {
                    viewModel.uploadStory(
                        currentUri!!,
                        description,
                        lat,
                        long,
                    )
                } else {
                    showToast(
                        requireActivity(),
                        getString(R.string.error_uploading_story)
                    )
                }
            }
            smLocation.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    getMyCurrentLocation()
                }
            }
        }
    }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireActivity(), permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private val requestPermissionLaunch = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                getMyCurrentLocation()
            }

            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                getMyCurrentLocation()
            }

            else -> {}
        }
    }

    private fun getMyCurrentLocation() {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) && checkPermission(
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    lat = location.latitude
                    long = location.longitude
                } else {
                    showToast(
                        requireActivity(),
                        getString(R.string.location_is_not_found_try_again)
                    )
                }
            }
        } else {
            requestPermissionLaunch.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun initObserver() {
        viewModel.uploadStoryResult.observe(viewLifecycleOwner) { result ->
            binding.apply {
                when (result) {
                    is ApiResponse.Loading -> loadingButton.root.visibility = View.VISIBLE

                    is ApiResponse.Success -> {
                        loadingButton.root.visibility = View.GONE
                        showToast(
                            requireActivity(),
                            result.data.message
                        )
                        findNavController().popBackStack()
                    }

                    is ApiResponse.Error -> {
                        loadingButton.root.visibility = View.GONE
                        showToast(
                            requireActivity(),
                            result.errorMessage
                        )
                    }

                    else -> {
                        loadingButton.root.visibility = View.GONE
                    }
                }
            }
        }
    }


}