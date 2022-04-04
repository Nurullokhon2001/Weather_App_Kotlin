package com.example.weatherappkotlin.business.repository

import com.example.weatherappkotlin.business.ApiProvider
import io.reactivex.rxjava3.subjects.BehaviorSubject

abstract class BaseRepository<T>(val api: ApiProvider) {

     val dateEmmiter: BehaviorSubject<T> = BehaviorSubject.create()
}