package com.davidarrozaqi.storyapp.ui.map

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.davidarrozaqi.storyapp.R
import com.davidarrozaqi.storyapp.base.BaseFragment
import com.davidarrozaqi.storyapp.data.dto.network.ApiResponse
import com.davidarrozaqi.storyapp.databinding.FragmentMapsBinding
import com.davidarrozaqi.storyapp.utils.showToast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.android.ext.android.inject

class MapsFragment : BaseFragment<FragmentMapsBinding>() {
    private val viewModel: MapsViewModel by inject()
    private val boundsBuilder = LatLngBounds.Builder()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentMapsBinding {
        return FragmentMapsBinding.inflate(inflater, container, false)
    }

    override fun doSomething() {
        super.doSomething()
        viewModel.getAllStoryWithLocation()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync { callback }
    }

    private val callback = OnMapReadyCallback { googleMap ->
        setMapSettings(googleMap)
        setMapStyle(googleMap)

        viewModel.storyResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ApiResponse.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                }

                is ApiResponse.Success -> {
                    binding.progressBar.isIndeterminate = false
                    binding.progressBar.progress = 100
                    result.data.listStory.forEach { story ->
                        val latLng = LatLng(story.lat ?: 0.0, story.lon ?: 0.0)
                        googleMap.addMarker(
                            MarkerOptions().position(latLng).title(story.name)
                                .snippet(story.description)
                        )
                        boundsBuilder.include(latLng)
                    }

                    val bounds: LatLngBounds = boundsBuilder.build()
                    googleMap.animateCamera(
                        CameraUpdateFactory.newLatLngBounds(
                            bounds,
                            resources.displayMetrics.widthPixels,
                            resources.displayMetrics.heightPixels,
                            300
                        )
                    )
                    binding.loading.visibility = View.GONE
                }

                is ApiResponse.Error -> {
                    binding.progressBar.isIndeterminate = false
                    binding.progressBar.progress = 100
                    showToast(
                        requireActivity(),
                        result.errorMessage
                    )
                    binding.tvLoading.text = getString(R.string.error)
                }

                else -> {}
            }
        }
    }

    private fun setMapStyle(googleMap: GoogleMap) {
        try {
            val success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    requireActivity(), R.raw.map_style
                )
            )
            if (!success) {
                showToast(requireActivity(), getString(R.string.style_parsing_failed))
            }
        } catch (e: Resources.NotFoundException) {
            showToast(requireActivity(), getString(R.string.can_t_find_style, e))
        }
    }

    private fun setMapSettings(googleMap: GoogleMap) {
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isIndoorLevelPickerEnabled = true
        googleMap.uiSettings.isCompassEnabled = true
        googleMap.uiSettings.isMapToolbarEnabled = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

}