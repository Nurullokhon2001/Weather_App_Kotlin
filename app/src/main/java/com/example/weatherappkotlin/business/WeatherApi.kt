package com.example.weatherappkotlin.business

import com.example.weatherappkotlin.business.model.WeatherDataModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/onecall?")
    fun getWeatherForecast(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("exclude") exclude: String = "minutely,alerts",
        @Query("appid") appid: String = "bc6618396256e12deef08922327e6149",
        @Query("lang") lang: String = "en"
    ) : Observable<WeatherDataModel>
}