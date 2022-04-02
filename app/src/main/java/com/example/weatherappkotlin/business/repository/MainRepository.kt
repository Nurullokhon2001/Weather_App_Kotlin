package com.example.weatherappkotlin.business.repository

import android.util.Log
import com.example.weatherappkotlin.business.ApiProvider
import com.example.weatherappkotlin.business.model.GeoCodeModel
import com.example.weatherappkotlin.business.model.WeatherDataModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observable.zip
import io.reactivex.rxjava3.core.Scheduler

import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers

class MainRepository(api: ApiProvider) : BaseRepository<MainRepository.ServerResponse>(api) {
    fun reloadData(lat: String, long: String) {
        Observable.zip(
            api.provideWeatherApi().getWeatherForecast(lat, long),
            api.provideGeoCodeApi().getCityByCoord(lat, long).map {
                it.asSequence()
                    .map { model -> model.name }
                    .toList()
                    .filterNotNull()
                    .first()
            },
            BiFunction<WeatherDataModel, String, ServerResponse> { first, second ->
                ServerResponse(
                    second,
                    first
                )
            }
        )
            .subscribeOn(Schedulers.io())
            .doOnNext {
                // TODO базава чиз медарорам
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