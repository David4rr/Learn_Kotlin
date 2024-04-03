package com.example.xgram.ui.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.xgram.ui.profile.FollowersFragment
import com.example.xgram.ui.profile.FollowingFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity, dataName: String) : FragmentStateAdapter(fragmentActivity) {

    private val username = dataName

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> {
                val fragment = FollowersFragment()
                fragment.arguments = Bundle().apply {
                    putString(FollowersFragment.ARG_USERNAME, username)
                    putInt(FollowersFragment.ARG_POSITION, position)
                }
                fragment
            }
            1 -> {
                val fragment = FollowingFragment()
                fragment.arguments = Bundle().apply {
                    putString(FollowingFragment.ARG_USERNAME, username)
                    putInt(FollowingFragment.ARG_POSITION, position)
                }
                fragment
            }
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}