package com.davidarrozaqi.storyapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.davidarrozaqi.storyapp.R
import com.davidarrozaqi.storyapp.adapter.StoryAdapter
import com.davidarrozaqi.storyapp.base.BaseFragment
import com.davidarrozaqi.storyapp.data.dto.network.ApiResponse
import com.davidarrozaqi.storyapp.databinding.FragmentHomeBinding
import com.davidarrozaqi.storyapp.utils.showToast
import org.koin.android.ext.android.inject

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val viewModel: HomeViewModel by inject()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun doSomething() {
        super.doSomething()
        viewModel.getAllStories()

        initAdapterList()
        initObserver()
        initListener()
    }

    private fun initListener() {
        binding.fabAddStory.setOnClickListener {
            val direction =
                HomeFragmentDirections.actionHomeFragmentToAddFragment()
            findNavController().navigate(direction)
        }
    }

    private fun initObserver() {
        with(viewModel) {
            username.observe(viewLifecycleOwner){
                binding.topBar.title = getString(R.string.hello_it, it)
            }
        }
    }

    private fun initAdapterList() {
        val storyAdapter = StoryAdapter()
        val linearLayoutManager =
            LinearLayoutManager(requireContext())
        binding.rvStory.adapter = storyAdapter
        binding.rvStory.layoutManager = linearLayoutManager
        viewModel.storyResult.observe(viewLifecycleOwner){
            when (it) {
                is ApiResponse.Loading -> {
                    binding.errorFetching.root.visibility = View.GONE
                    binding.cpIndicator.visibility = View.VISIBLE
                }

                is ApiResponse.Success -> {
                    binding.cpIndicator.visibility = View.GONE
                    storyAdapter.submitList(it.data.listStory)
                }

                else -> {
                    binding.cpIndicator.visibility = View.GONE
                    binding.errorFetching.root.visibility = View.VISIBLE
                    showToast(
                        requireActivity(),
                        getString(R.string.error_fetching)
                    )
                }
            }
        }
    }

}