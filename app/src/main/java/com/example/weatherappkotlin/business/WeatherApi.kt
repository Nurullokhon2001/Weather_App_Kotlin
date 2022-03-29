package com.example.weatherappkotlin.business

import android.database.Observable
import com.example.weatherappkotlin.business.model.WeatherDataModel
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/onecall?")
    fun getWeatherForecast(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("exclude") exclude: String = "minutely,alerts",
        @Query("appid") appid: String = "da0f8dee0d99fed9106437bf05e879ad",
        @Query("lang") lang: String = "en"
    ) : Observable<WeatherDataModel>
}