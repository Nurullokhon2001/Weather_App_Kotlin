package com.example.weatherappkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherappkotlin.business.model.GeoCodeModel
import com.example.weatherappkotlin.view.MenuView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_menu.*
import moxy.MvpAppCompatActivity
import java.util.concurrent.TimeUnit

class MenuActivity : MvpAppCompatActivity(),MenuView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        search_field.createObservable()
            .doOnNext { setLoading(true) }
            .debounce(700, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                //TODO  (MenuActivity) if(!it.isNullOrEmpty)  presenter.getFavoriteList()
            }

    }

    override fun setLoading(flag: Boolean) {
        TODO("Not yet implemented")
    }

    override fun fillPredictionList(data: List<GeoCodeModel>) {
        TODO("Not yet implemented")
    }

    override fun fillFavoriteList(data: List<GeoCodeModel>) {
        TODO("Not yet implemented")
    }
}