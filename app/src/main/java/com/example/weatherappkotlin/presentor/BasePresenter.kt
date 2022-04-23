package com.example.weatherappkotlin.presentor

import moxy.MvpPresenter
import moxy.MvpView

abstract class BasePresenter<T : MvpView> : MvpPresenter<T>() {

    abstract fun  enable()

}