package com.example.android2022.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val title: String,
    val description: String,

    val date: Date? = null,
    val longitude: Double? = null,
    val latitude: Double? = null

)
