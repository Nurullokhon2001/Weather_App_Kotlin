package com.example.weatherappkotlin.business.repository

import com.example.weatherappkotlin.business.ApiProvider
import com.example.weatherappkotlin.business.model.GeoCodeModel
import com.example.weatherappkotlin.business.room.entity.GeoCodeEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


const val SAVED = 1
const val CURRENT = 0

class MenuRepository(api: ApiProvider) : BaseRepository<MenuRepository.Content>(api) {

    private val dbAccess = db?.getGeoCodeDao()

    fun getCities(name: String) {
        api.provideGeoCodeApi().getCityByName(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                Content(it, CURRENT)
            }
            .subscribe {
                dateEmmiter.onNext(it)
            }
    }

    fun add(data: GeoCodeModel) {
        getFavoriteListWith { dbAccess!!.add(item = data as GeoCodeEntity) }
    }

    fun remove(data: GeoCodeModel) {
        getFavoriteListWith { dbAccess!!.remove(data as GeoCodeEntity) }
    }

    fun update() {
        getFavoriteListWith()
    }

    private fun getFavoriteListWith(daoQuery: (() -> Unit)? = null) {
        roomTransaction {
            daoQuery?.let { it() }
            Content(dbAccess!!.getAll().map { it as GeoCodeModel }, SAVED)
        }
    }

    data class Content(val data: List<GeoCodeModel>, val purpose: Int)
}