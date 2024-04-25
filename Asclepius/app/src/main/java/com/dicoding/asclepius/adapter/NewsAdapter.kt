package com.dicoding.asclepius.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.model.ArticlesItem
import com.dicoding.asclepius.databinding.ListNewsBinding

class NewsAdapter : ListAdapter<ArticlesItem, NewsAdapter.NewsViewHolder>(DIFF_CALLBACK) {
    class NewsViewHolder(private val binding: ListNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ArticlesItem) {
            val requestOptions = RequestOptions()
                .transform(CenterCrop(), RoundedCorners(16))
                .placeholder(R.drawable.ic_place_holder)

            Glide.with(binding.root.context)
                .load(item.urlToImage)
                .apply(requestOptions)
                .into(binding.ivPicture)
            binding.tvTitle.text = item.title
            binding.tvDescription.text = item.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ListNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticlesItem>() {
            override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}