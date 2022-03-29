package com.example.weatherappkotlin.business

import android.database.Observable
import com.example.weatherappkotlin.business.model.GeoCodeModel
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoCodingApi {

    @GET("get/1.0/reverse?")
    fun getCityByCoord(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("limit") limit: String = "10",
        @Query("appid") appid: String = "da0f8dee0d99fed9106437bf05e879ad"
    ): Observable<GeoCodeModel>

}