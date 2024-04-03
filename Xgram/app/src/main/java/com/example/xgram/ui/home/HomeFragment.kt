package com.example.xgram.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xgram.R
import com.example.xgram.ui.adapter.UserAdapter
import com.example.xgram.data.model.HomeViewModel
import com.example.xgram.data.response.UserResponseItems
import com.example.xgram.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private val homeViewModel: HomeViewModel by viewModels()

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (binding?.searchView?.isShowing == true) {
                binding?.searchView?.hide()
            } else {
                isEnabled = false
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backPressedCallback)

        setupListUser()
        setupSearchView()

        homeViewModel.listUser.observe(viewLifecycleOwner) { listUser ->
            listUser?.let { setUserData(it) }
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun setupSearchView() {
        with(binding) {
            this?.searchView?.setupWithSearchBar(searchBar)
            this?.searchView?.editText?.setOnEditorActionListener { textView, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch(textView.text?.toString())
                    true
                } else {
                    false
                }
            }
            this?.searchView?.hide()
            this?.searchBar?.inflateMenu(R.menu.menu_item)
            this?.searchBar?.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId){
                    R.id.action_favorite -> {
                    val toProfile = HomeFragmentDirections.actionHomeFragmentToFavoriteFragment()
                        view?.findNavController()?.navigate(toProfile)
                        true
                    }
                    R.id.action_theme -> {
                        val toSetting = HomeFragmentDirections.actionHomeFragmentToSettingFragment()
                        view?.findNavController()?.navigate(toSetting)
                        true
                    }
                    else -> true
                }
            }
        }
    }

    private fun performSearch(query: String?) {
        val userQuery = query?.takeIf { it.isNotBlank() } ?: "david"
        binding?.searchView?.hide()
        binding?.searchBar?.setText(userQuery)
        homeViewModel.searchUser(userQuery)

        if (query.isNullOrEmpty()) {
            binding?.searchBar?.setText("")
        }
    }

    private fun setupListUser() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding?.rvListPerson?.layoutManager = layoutManager
    }

    private fun setUserData(user: List<UserResponseItems>) {
        val adapter = UserAdapter()
        adapter.submitList(user)
        binding?.rvListPerson?.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}