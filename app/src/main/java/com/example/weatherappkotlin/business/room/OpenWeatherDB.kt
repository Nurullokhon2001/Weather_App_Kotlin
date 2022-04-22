package com.example.weatherappkotlin.business.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherappkotlin.business.room.dao.GeoCodeDao
import com.example.weatherappkotlin.business.room.dao.WeatherDao
import com.example.weatherappkotlin.business.room.entity.GeoCodeEntity
import com.example.weatherappkotlin.business.room.entity.WeatherDataEntity

@Database(entities = [WeatherDataEntity::class,GeoCodeEntity::class], exportSchema = false, version = 2)
abstract class OpenWeatherDB : RoomDatabase() {

    abstract fun getWeatherDao(): WeatherDao

    abstract fun getGeoCodeDao(): GeoCodeDao

}