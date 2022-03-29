package com.example.weatherappkotlin.presentor

import com.example.weatherappkotlin.view.MainView

 class MainPresenter : BasePresenter<MainView>() {

    override fun enable() {

    }

    fun refresh (lat: String, long : String){
        viewState.setLoading(true)
    }
}