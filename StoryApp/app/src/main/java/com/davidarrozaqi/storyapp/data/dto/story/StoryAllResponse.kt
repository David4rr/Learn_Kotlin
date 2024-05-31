package com.davidarrozaqi.storyapp.data.dto.story

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
data class StoryALlResponse(
    @Json(name = "listStory")
    val listStory: List<StoryResponse?>? = null,

    @Json(name = "error")
    val error: Boolean,

    @Json(name = "message")
    val message: String
)

@Parcelize
@JsonClass(generateAdapter = true)
data class StoryResponse(

    @Json(name = "photoUrl")
    val photoUrl: String? = null,

    @Json(name = "createdAt")
    val createdAt: String? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "description")
    val description: String? = null,

    @Json(name = "lon")
    val lon: Float? = null,

    @Json(name = "id")
    val id: String? = null,

    @Json(name = "lat")
    val lat: Float? = null
) : Parcelable