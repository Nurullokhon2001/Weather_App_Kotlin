package com.example.weatherappkotlin.business.repository

import com.example.weatherappkotlin.business.ApiProvider
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

    fun add(data: GeoCodeEntity) {
        getFavoriteListWith { dbAccess!!.add(item = data ) }
    }

    fun remove(data: GeoCodeEntity) {
        getFavoriteListWith { dbAccess!!.remove(data) }
    }

    fun update() {
        getFavoriteListWith()
    }

    private fun getFavoriteListWith(daoQuery: (() -> Unit)? = null) {
        roomTransaction {
            daoQuery?.let { it() }
            Content(dbAccess!!.getAll().map { it }, SAVED)
        }
    }

    data class Content(val data: List<GeoCodeEntity>, val purpose: Int)
}