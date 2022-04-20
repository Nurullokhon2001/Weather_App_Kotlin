package com.example.weatherappkotlin.business.room

import androidx.room.*

@Dao
interface WeatherDao {


    @Query("SELECT * from WeatherData Where id = 1")
    fun getWeatherData(): WeatherDataEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherData(data: WeatherDataEntity)

    @Delete
    fun deleteWeatherData(data: WeatherDataEntity)

    @Update
    fun updateWeatherData(data: WeatherDataEntity)
}