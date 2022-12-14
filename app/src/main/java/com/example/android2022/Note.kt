package com.example.android2022

import java.util.*

//id, title, description.
//- И необязательных date, longitude, latitude.
data class Note(
    val id: Int,
    val title: String,
    val description: String,

    val date: Date,
    val longitude: Double,
    val latitude: Double
)
