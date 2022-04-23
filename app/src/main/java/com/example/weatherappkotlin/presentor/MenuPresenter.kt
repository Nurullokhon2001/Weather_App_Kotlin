package com.example.weatherappkotlin.presentor


import android.util.Log
import com.example.weatherappkotlin.business.ApiProvider
import com.example.weatherappkotlin.business.repository.MenuRepository
import com.example.weatherappkotlin.business.repository.SAVED
import com.example.weatherappkotlin.business.room.entity.GeoCodeEntity
import com.example.weatherappkotlin.view.MenuView

class MenuPresenter : BasePresenter<MenuView>() {

    private val repo = MenuRepository(ApiProvider())

    override fun enable() {
        repo.dateEmmiter.subscribe {
            viewState.setLoading(false)
            if (it.purpose == SAVED) {
                Log.d("enable", "enable: SAVED ${it.data}")
                viewState.fillFavoriteListt(it.data)
            } else {
                Log.d("enable", "enable: CURRENT ${it.data}")

                viewState.fillPredictionListt(it.data)
            }
        }
    }

    fun searchFor(str: String) {
        repo.getCities(str)
    }

    fun removeLocation(data: GeoCodeEntity) {
        repo.remove(data)
    }

    fun saveLocation(data: GeoCodeEntity) {
        repo.add(data)
    }

    fun getFavoriteList() {
        repo.update()
    }

}