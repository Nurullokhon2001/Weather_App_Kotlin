package com.example.weatherappkotlin.view

import com.example.weatherappkotlin.business.model.GeoCodeModel
import com.example.weatherappkotlin.business.room.entity.GeoCodeEntity
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface MenuView : MvpView {

    @AddToEndSingle
    fun setLoading(flag: Boolean)

    @AddToEndSingle
    fun fillPredictionListt(data: List<GeoCodeEntity>)

    @AddToEndSingle
    fun fillFavoriteListt(data: List<GeoCodeEntity>)
}