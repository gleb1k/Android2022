package com.example.android2022.model

sealed interface MainItem {

    data class Car(
        val name: String,
        val brand: String,
        val info: String,
        val url: String,
    ) : MainItem

    data class Advertisement(
        val title: String,
        val url: String
    ): MainItem
}