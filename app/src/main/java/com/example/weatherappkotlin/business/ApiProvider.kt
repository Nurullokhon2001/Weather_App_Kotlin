package com.example.weatherappkotlin.business

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiProvider {
    private val openWeatherMap: Retrofit by lazy { initApi() }


    private val okHttpClient = OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()


    private fun initApi() = Retrofit.Builder()
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.openweathermap.org/")
        .client(okHttpClient)
        .build()

    fun provideWeatherApi(): WeatherApi = openWeatherMap.create(WeatherApi::class.java)

    fun provideGeoCodeApi(): GeoCodingApi = openWeatherMap.create(GeoCodingApi::class.java)

}