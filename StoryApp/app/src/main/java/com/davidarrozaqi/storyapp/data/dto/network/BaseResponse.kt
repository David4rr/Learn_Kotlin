package com.davidarrozaqi.storyapp.data.dto.network

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
abstract class BaseResponse (

    @field:SerializedName("error")
    val error: Boolean = false,

    @field:SerializedName("message")
    val message: String = ""

)
