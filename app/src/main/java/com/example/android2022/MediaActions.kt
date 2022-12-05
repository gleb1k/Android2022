package com.example.android2022

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class MediaActions(val key: Int) : Parcelable {

    PLAY(0), PAUSE(1), STOP(2), PREV(3), NEXT(4)
}