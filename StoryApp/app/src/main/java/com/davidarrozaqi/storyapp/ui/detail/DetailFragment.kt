package com.davidarrozaqi.storyapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.davidarrozaqi.storyapp.base.BaseFragment
import com.davidarrozaqi.storyapp.data.dto.story.StoryResponse
import com.davidarrozaqi.storyapp.databinding.FragmentDetailBinding

class DetailFragment : BaseFragment<FragmentDetailBinding>() {
    var item: StoryResponse? = null

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDetailBinding {
        return FragmentDetailBinding.inflate(inflater, container, false)
    }

    override fun doSomething() {
        super.doSomething()
        item = DetailFragmentArgs.fromBundle(requireArguments()).story

        with(binding) {
            Glide.with(ivStoryImage.context)
                .load(item?.photoUrl)
                .into(ivStoryImage)
            tvUserStory.text = item?.name
            tvDescription.text = item?.description
            tvTimestamp.text = item?.createdAt
            topBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

}