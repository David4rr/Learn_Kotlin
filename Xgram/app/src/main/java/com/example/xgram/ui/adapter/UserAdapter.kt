package com.example.xgram.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.xgram.data.response.UserResponseItems
import com.example.xgram.databinding.ItemListpersonBinding
import com.example.xgram.ui.home.HomeFragmentDirections


class UserAdapter : ListAdapter<UserResponseItems, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding: ItemListpersonBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(user: UserResponseItems){
        binding.tvName.text = user.login
        Glide.with(binding.root.context)
            .load(user.avatarUrl)
            .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(16)))
            .into(binding.ivProfile)
            binding.root.setOnClickListener { view ->
            val toProfileFragment = HomeFragmentDirections.actionGlobalProfileFragment(
                user.login.toString()
            )
            view.findNavController().navigate(toProfileFragment)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemListpersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserResponseItems>(){
            override fun areItemsTheSame(oldItem: UserResponseItems, newItem: UserResponseItems): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: UserResponseItems, newItem: UserResponseItems): Boolean {
                return oldItem == newItem
            }
        }
    }
}