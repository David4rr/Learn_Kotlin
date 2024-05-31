package com.davidarrozaqi.storyapp.ui.add

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.davidarrozaqi.storyapp.R
import com.davidarrozaqi.storyapp.base.BaseFragment
import com.davidarrozaqi.storyapp.data.dto.network.ApiResponse
import com.davidarrozaqi.storyapp.databinding.FragmentAddBinding
import com.davidarrozaqi.storyapp.utils.getImageUri
import com.davidarrozaqi.storyapp.utils.showToast
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
        binding.ivStoryImage.setImageURI(currentUri)
    }

    override fun doSomething() {
        super.doSomething()

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
                    )
                } else {
                    showToast(
                        requireActivity(),
                        getString(R.string.error_uploading_story)
                    )
                }
            }
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