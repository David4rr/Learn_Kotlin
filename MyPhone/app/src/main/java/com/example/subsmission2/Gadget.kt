package com.example.subsmission2

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Gadget(
    val name: String,
    val description: String,
    val secondDescription: String,
    val photo: String
) : Parcelable
