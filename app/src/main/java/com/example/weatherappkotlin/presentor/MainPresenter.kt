package com.example.weatherappkotlin.presentor

import android.util.Log
import com.example.weatherappkotlin.business.ApiProvider
import com.example.weatherappkotlin.business.repository.MainRepository
import com.example.weatherappkotlin.view.MainView

class MainPresenter : BasePresenter<MainView>() {

    private val repo = MainRepository(ApiProvider())

    override fun enable() {
        repo.dateEmmiter.subscribe { response ->
            Log.d("MainRepo", "Presenter enabled(): $response ")
            viewState.displayLocation(response.cityName)
            viewState.displayCurrentData(response.weatherData)
            viewState.displayDailyData(response.weatherData.daily)
            viewState.displayHourlyData(response.weatherData.hourly)
            response.error?.let {
                viewState.displayError(response.error)
            }
        }
    }

    fun refresh(lat: String, long: String) {
        viewState.setLoading(true)

        repo.reloadData("33.44", "-94.04")
    }
}