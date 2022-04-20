package com.example.weatherappkotlin.business.repository

import android.util.Log
import com.example.weatherappkotlin.business.ApiProvider
import com.example.weatherappkotlin.business.model.GeoCodeModel
import com.example.weatherappkotlin.business.model.WeatherDataModel
import com.example.weatherappkotlin.business.room.WeatherDataEntity
import com.google.gson.Gson
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observable.zip
import io.reactivex.rxjava3.core.Scheduler

import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers

class MainRepository(api: ApiProvider) : BaseRepository<MainRepository.ServerResponse>(api) {

   private val gson = Gson()

    private  val dbAccess = db!!.getWeatherDao()


    fun reloadData(lat: String, long: String) {
       zip(
            api.provideGeoCodeApi().getCityByCoord(lat, long).map {
                it.asSequence()
                    .map { model -> model.name }
                    .toList()
                    .filterNotNull()
                    .first()
            },
            api.provideWeatherApi().getWeatherForecast(lat, long),
            BiFunction<String, WeatherDataModel, ServerResponse> { first, second ->
                ServerResponse(
                    first,
                    second
                )
            }
        )
            .subscribeOn(Schedulers.io())
            .doOnNext {
                dbAccess.insertWeatherData(
                    WeatherDataEntity(
                        data = gson.toJson(it.weatherData),
                        city = it.cityName
                    )
                )
            }
            .onErrorResumeNext {
                Observable.just(
                    ServerResponse(
                        dbAccess.getWeatherData().city,
                        gson.fromJson(dbAccess.getWeatherData().data, WeatherDataModel::class.java),
                        it
                    )
                )
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                dateEmmiter.onNext(it)
            }, {
                Log.d("MainRepo", "reloadData: $it ")
            })

    }

    data class ServerResponse(
        val cityName: String,
        val weatherData: WeatherDataModel,
        val error: Throwable? = null
    )

}