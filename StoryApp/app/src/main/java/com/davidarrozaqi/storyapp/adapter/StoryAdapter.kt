package com.davidarrozaqi.storyapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.davidarrozaqi.storyapp.data.dto.story.StoryResponse
import com.davidarrozaqi.storyapp.databinding.StoryItemBinding
import com.davidarrozaqi.storyapp.ui.home.HomeFragmentDirections

class StoryAdapter : PagingDataAdapter<StoryResponse, StoryAdapter.StoryViewHolder>(DIFF_CALLBACK) {

    class StoryViewHolder(val binding: StoryItemBinding) : ViewHolder(binding.root) {
        fun bind(item: StoryResponse) {
            with(binding) {
                Glide.with(ivStoryImage.context)
                    .load(item.photoUrl)
                    .into(ivStoryImage)
                tvUserStory.text = item.name
                tvDescription.text = item.description
                tvTimestamp.text = item.createdAt

                root.setOnClickListener {
                    val direction =
                        HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                            item
                        )
                    root.findNavController().navigate(direction)
                }
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val itemStory = StoryItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StoryViewHolder(itemStory)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) holder.bind(item)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryResponse>() {
            override fun areItemsTheSame(oldItem: StoryResponse, newItem: StoryResponse): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: StoryResponse,
                newItem: StoryResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}