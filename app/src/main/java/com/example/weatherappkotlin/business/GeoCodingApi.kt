package com.example.weatherappkotlin.business

import com.example.weatherappkotlin.business.model.GeoCodeModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoCodingApi {


   // http://api.openweathermap.org/geo/1.0/reverse?lat=51.5098&lon=-0.1180&limit=5&appid={API key}

    @GET("geo/1.0/reverse?")
    fun getCityByCoord(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("limit") limit: String = "10",
        @Query("appid") appid: String = "bc6618396256e12deef08922327e6149"
    ): Observable<List<GeoCodeModel>>

}