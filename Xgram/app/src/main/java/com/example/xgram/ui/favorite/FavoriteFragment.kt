package com.example.xgram.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xgram.data.factory.ViewModelFactory
import com.example.xgram.data.model.FavoriteViewModel
import com.example.xgram.data.response.UserResponseItems
import com.example.xgram.databinding.FragmentFavoriteBinding
import com.example.xgram.ui.adapter.UserAdapter


class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding
    private val viewModel: FavoriteViewModel by viewModels {
        ViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.topBar?.apply {
            setNavigationOnClickListener {
                activity?.onBackPressedDispatcher?.onBackPressed()
            }
            title = "Favorite"
        }

        val adapter = UserAdapter()
        binding?.rvFavorite?.adapter = adapter

        viewModel.getAllFavorite().observe(viewLifecycleOwner){
            users -> val items = arrayListOf<UserResponseItems>()
            users.map {
                val item = UserResponseItems(login = it.username, avatarUrl = it.avatarUrl)
                items.add(item)
            }
            viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                binding?.lpiFavorite?.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
            adapter.submitList(items)
        }
        setRecyclerView()
    }

    private fun setRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding?.rvFavorite?.layoutManager = layoutManager
    }

}