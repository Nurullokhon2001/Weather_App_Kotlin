package com.example.weatherappkotlin.business.repository

import com.example.weatherappkotlin.App
import com.example.weatherappkotlin.business.ApiProvider
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject

abstract class BaseRepository<T>(val api: ApiProvider) {

    val dateEmmiter: BehaviorSubject<T> = BehaviorSubject.create()
    protected val db by lazy { App.db }

    protected fun roomTransaction(
        transaction: () -> T
    ): Disposable = Observable.fromCallable { transaction() }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { dateEmmiter.onNext(it) }
}