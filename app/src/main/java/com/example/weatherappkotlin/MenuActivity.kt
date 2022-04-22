package com.example.weatherappkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherappkotlin.business.model.GeoCodeModel
import com.example.weatherappkotlin.view.MenuView
import com.example.weatherappkotlin.view.adapters.CityAdapter
import com.example.weatherappkotlin.view.createObservable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_menu.*
import moxy.MvpAppCompatActivity
import java.util.concurrent.TimeUnit

class MenuActivity : MvpAppCompatActivity(), MenuView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        initCityList(productions)
        initCityList(favorites)

        search_field.createObservable()
            .doOnNext { setLoading(true) }
            .debounce(700, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                //TODO  (MenuActivity) if(!it.isNullOrEmpty)  presenter.getFavoriteList()
            }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, R.anim.slide_out_left)
    }

    override fun setLoading(flag: Boolean) {
        loading.isActivated = flag
        loading.visibility = if (flag) View.VISIBLE else View.GONE
    }

    override fun fillPredictionList(data: List<GeoCodeModel>) {
        (productions.adapter as CityAdapter).updateData(data,this)
    }

    override fun fillFavoriteList(data: List<GeoCodeModel>) {
        (favorites.adapter as CityAdapter).updateData(data,this)
    }


    private fun initCityList(rv: RecyclerView) {
        val cityAdapter = CityAdapter()
        cityAdapter.clickListener = searchItemClickListener
        rv.apply {
            adapter = cityAdapter
            layoutManager = object : LinearLayoutManager(this@MenuActivity, VERTICAL, false) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }
            setHasFixedSize(true)
        }
    }

    private val searchItemClickListener = object : CityAdapter.SearchItemClickListener {
        override fun addToFavorite(item: GeoCodeModel) {
            TODO("Not yet implemented")
        }

        override fun removeFromFavorite(item: GeoCodeModel) {
            TODO("Not yet implemented")
        }

        override fun showWeatherIn(item: GeoCodeModel) {
            val intent = Intent(this@MenuActivity, MainActivity::class.java)
            val bundle = Bundle()
            bundle.putString("lat", item.lat.toString())
            bundle.putString("lon", item.lon.toString())
            intent.putExtra("Coordinate", bundle)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, R.anim.slide_out_left)
        }

    }

}