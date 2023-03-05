package com.example.giphy.data.api

import com.example.giphy.data.model.GiphyList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {


    @GET("search")
    suspend fun getGiphyList(
        @Query("q") q: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<GiphyList>

    @GET("{id}")
    suspend fun getDetailGiphy(
        @Path("id") id: String
    ): Response<com.example.giphy.data.model.GiphyDetails>


}