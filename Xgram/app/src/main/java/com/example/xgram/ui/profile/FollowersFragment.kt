package com.example.xgram.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xgram.ui.adapter.UserAdapter
import com.example.xgram.data.model.FollowersViewModel
import com.example.xgram.databinding.FragmentFollowersBinding

class FollowersFragment : Fragment() {

    private var _username: String? = null
    private var _position: Int? = null
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding
    private val followersViewModel: FollowersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            _username = it.getString(ARG_USERNAME)
            _position = it.getInt(ARG_POSITION)
        }

        val adapter = UserAdapter()

        followersRecyclerView()
        showData(adapter)
    }

    private fun showData(adapter: UserAdapter) {
        with(followersViewModel){
            if (followers.value == null){
                setFollowers(_username!!)
            }
            followers.observe(viewLifecycleOwner){

                adapter.submitList(it)
                binding?.rvFollowers?.adapter = adapter
            }
            isLoading.observe(viewLifecycleOwner) {
                showLoad(it)
            }
        }
    }

    private fun showLoad(isLoadings: Boolean) {
        if (isLoadings){
            binding?.progressBar?.visibility = View.VISIBLE
            binding?.rvFollowers?.visibility = View.INVISIBLE
        } else {
            binding?.progressBar?.visibility = View.INVISIBLE
            binding?.rvFollowers?.visibility = View.VISIBLE
        }
    }

    private fun followersRecyclerView() {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding?.rvFollowers?.layoutManager = layoutManager
    }

    companion object {
        const val ARG_POSITION = "arg_position"
        const val ARG_USERNAME = "arg_username"
    }
}