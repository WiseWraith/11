package com.example.gameb.model


import kotlinx.serialization.Serializable

@Serializable
data class Game(
    val im_modelID: Int,
    val iconUrl: String,
    val model: String,
    val description: String,
    val genre: Genre
)

