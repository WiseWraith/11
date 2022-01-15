package com.example.gameb.data.network

import com.example.gameb.model.Game
import com.example.gameb.model.Review
import com.example.gameb.model.Shop
import retrofit2.http.GET

interface RestApi {
    @GET("Game")
    suspend fun loadGames(): List<Game>
    @GET("Shops")
    suspend fun loadShops(): List<Shop>
    @GET("Reviews")
    suspend fun loadReviews(): List<Review>
}