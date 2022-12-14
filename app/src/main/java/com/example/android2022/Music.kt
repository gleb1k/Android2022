package com.example.android2022

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes

data class Music(
    val name: String,
    val author: String,
    val genre: String,
    @DrawableRes val cover: Int,
    @RawRes val audio: Int,
    )