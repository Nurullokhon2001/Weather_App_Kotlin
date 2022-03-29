package com.example.weatherappkotlin.presentor

import com.example.weatherappkotlin.view.MainView
import moxy.MvpPresenter

abstract class BasePresenter<T : MainView> : MvpPresenter<T>() {

    abstract fun  enable()

}