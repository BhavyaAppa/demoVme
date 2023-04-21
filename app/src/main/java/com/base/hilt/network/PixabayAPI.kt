package com.base.hilt.network

import com.base.hilt.model.response.ImageResponse
import com.base.hilt.model.response.LocationResp
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayAPI {

    @GET("/api/")
     fun searchForImage(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = "28066941-b071228f474ce1f9a04f5df53"
    ): Response<ImageResponse>

    @GET("/location")
    fun getCitiesList(): Response<LocationResp>
}