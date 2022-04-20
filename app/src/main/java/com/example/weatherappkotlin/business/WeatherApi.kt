package com.example.weatherappkotlin.business

import com.example.weatherappkotlin.business.model.WeatherDataModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    // https://api.openweathermap.org/data/2.5/onecall?lat=33.44&lon=-94.04&exclude=minutely&appid=bc6618396256e12deef08922327e6149
    //   https://api.openweathermap.org/get/1.0/reverse?&lat=33.44&lon=-94.04&limit=10&appid=bc6618396256e12deef08922327e6149
    //  https://api.openweathermap.org/

    @GET("data/2.5/onecall?")
    fun getWeatherForecast(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("exclude") exclude: String = "minutely",
        @Query("appid") appid: String = "bc6618396256e12deef08922327e6149",
        @Query("lang") lang: String = "en"
    ): Observable<WeatherDataModel>
}