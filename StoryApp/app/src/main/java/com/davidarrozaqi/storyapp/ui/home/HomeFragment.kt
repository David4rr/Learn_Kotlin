package com.davidarrozaqi.storyapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.davidarrozaqi.storyapp.R
import com.davidarrozaqi.storyapp.adapter.LoadingStateAdapter
import com.davidarrozaqi.storyapp.adapter.StoryAdapter
import com.davidarrozaqi.storyapp.base.BaseFragment
import com.davidarrozaqi.storyapp.databinding.FragmentHomeBinding
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
            username.observe(viewLifecycleOwner) {
                binding.topBar.title = getString(R.string.hello_it, it)
            }
        }
    }

    private fun initAdapterList() {
        val storyAdapter = StoryAdapter()
        val linearLayoutManager =
            LinearLayoutManager(requireContext())
        binding.rvStory.adapter = storyAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                storyAdapter.retry()
            }
        )
        binding.rvStory.layoutManager = linearLayoutManager
        viewModel.storyResult.observe(viewLifecycleOwner) {
            storyAdapter.submitData(lifecycle, it)
        }
        storyAdapter.refresh()
    }

    override fun onResume() {
        initAdapterList()
        super.onResume()
    }

}