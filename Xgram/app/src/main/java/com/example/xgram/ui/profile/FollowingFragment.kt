package com.example.xgram.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xgram.ui.adapter.UserAdapter
import com.example.xgram.data.model.FollowingViewModel
import com.example.xgram.databinding.FragmentFollowingBinding

class FollowingFragment : Fragment() {
    
    private var _position: Int? = null
    private var _username: String? = null
    private var _binding: FragmentFollowingBinding? = null

    private val binding get() = _binding
    private val followingViewModel: FollowingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            _position = it.getInt(ARG_POSITION)
            _username = it.getString(ARG_USERNAME)
        }

        val adapter = UserAdapter()
        showFollowing(adapter)

        getFollowing()

    }

    private fun showFollowing(adapter: UserAdapter) {
        with(followingViewModel){
            if (following.value == null){
                setFollowing(_username!!)
                following.observe(viewLifecycleOwner){
                    adapter.submitList(it)
                    binding?.rvFollowing?.adapter = adapter
                }
                isLoading.observe(viewLifecycleOwner){
                    showLoad(it)
                }
            }
        }
    }

    private fun showLoad(isLoadings: Boolean) {
        if (isLoadings) {
            binding?.rvFollowing?.visibility = View.VISIBLE
            binding?.rvFollowing?.visibility = View.INVISIBLE
        } else {
            binding?.rvFollowing?.visibility = View.INVISIBLE
            binding?.rvFollowing?.visibility = View.VISIBLE
        }
    }

    private fun getFollowing() {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding?.rvFollowing?.layoutManager = layoutManager
    }

    companion object {
        const val ARG_POSITION = "arg_position"
        const val ARG_USERNAME = "arg_username"
    }

}