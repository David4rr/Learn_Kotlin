package com.example.xgram.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.xgram.R
import com.example.xgram.data.db.FavoriteEntity
import com.example.xgram.data.factory.ViewModelFactory
import com.example.xgram.ui.adapter.ViewPagerAdapter
import com.example.xgram.data.model.ProfileViewModel
import com.example.xgram.data.response.DetailUserResponse
import com.example.xgram.databinding.FragmentProfileBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding
    private val profileViewModel: ProfileViewModel by viewModels<ProfileViewModel>{
        ViewModelFactory.getInstance(requireActivity().application)
    }
    private var saveUsername: String? = null
    private var saveUser: String? = null
    private var isFavorite: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.topBar?.apply {
            setNavigationOnClickListener {
                activity?.onBackPressedDispatcher?.onBackPressed()
            }
            title = "Detail"
        }

        val dataName = arguments?.getString("username")
        if (profileViewModel.userDetails.value == null){
            profileViewModel.getUserDetail(dataName!!)
        }

        profileViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding?.linearProgress?.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        profileViewModel.userDetails.observe(viewLifecycleOwner){
            setUserDetail(it!!)
        }

        val viewPagerAdapter = ViewPagerAdapter(requireActivity(), dataName!!)
        val viewPager: ViewPager2 = binding?.viewPager!!
        viewPager.adapter = viewPagerAdapter
        val tabs: TabLayout = binding?.tabLayout!!
        TabLayoutMediator(tabs, viewPager){tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        val inNames = arguments?.getString("username")
        profileViewModel.getFavoriteByUsername(inNames ?: "").observe(viewLifecycleOwner){
            favoriteUser -> isFavorite = favoriteUser != null
            updateFavoriteIcon()
        }

        binding?.fabFav?.setOnClickListener{
            val favoriteUser = saveUsername?.let { username ->
                saveUser?.let { avatarUrl ->
                    FavoriteEntity(username = username.substring(1), avatarUrl = avatarUrl)
                }
            }
            favoriteUser?.let { user ->
                if (isFavorite){
                    profileViewModel.deleteFavorite(user)
                    profileViewModel.inResult.observe(viewLifecycleOwner){isSuccess ->
                        if (isSuccess){
                           Toast.makeText(context, "User deleted from favorite", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Failed delete user from favorite", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    profileViewModel.insertFavorite(user)
                    profileViewModel.inResult.observe(viewLifecycleOwner){isSuccess ->
                        if (isSuccess){
                            Toast.makeText(context, "User added to favorite", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Failed to add user from favorite", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } ?: run {
                Toast.makeText(context, "User data is incomplete", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateFavoriteIcon(){
        binding?.fabFav?.setImageResource(
            if (isFavorite) R.drawable.favorite else R.drawable.favorite_border
        )
    }

    private fun setUserDetail(user: DetailUserResponse){
        val avatarUrl = "${user.avatarUrl}"
        val detailUsername = "@${user.login}"
        with(binding){
            Glide.with(this!!.root.context)
                .load(user.avatarUrl)
                .placeholder(R.drawable.account_circle)
                .circleCrop()
                .into(ivDetailProfile)
            tvDetailName.text = user.name ?: "-"
            tvUsername.text = detailUsername
            tvFollowing.text = getString(R.string.following, user.following)
            tvFollowers.text =getString(R.string.followers, user.followers)
        }
        saveUsername = detailUsername
        saveUser = avatarUrl
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers_tab,
            R.string.following_tab
        )
    }

}