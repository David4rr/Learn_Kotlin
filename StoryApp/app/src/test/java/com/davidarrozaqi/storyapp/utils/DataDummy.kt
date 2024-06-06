package com.davidarrozaqi.storyapp.utils

import com.davidarrozaqi.storyapp.data.dto.story.StoryResponse

object DataDummy {

    fun generateDummyStory(): List<StoryResponse> {
        val items: MutableList<StoryResponse> = arrayListOf()
        for (i in 0..100) {
            val story = StoryResponse(
                id = i.toString(),
                name = "Story #$i",
                description = "Description: #$i",
                photoUrl = "https://picsum.photos/600/400?random=$i",
                createdAt = "2024-09-17T00:00:00Z",
                lat = (-1.17),
                lon = 117.09
            )
            items.add(story)
        }
        return items
    }
}