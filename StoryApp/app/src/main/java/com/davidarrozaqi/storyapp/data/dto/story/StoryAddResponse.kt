package com.davidarrozaqi.storyapp.data.dto.story

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StoryAddResponse(

    @Json(name = "error")
    val error: Boolean,

    @Json(name = "message")
    val message: String
)
