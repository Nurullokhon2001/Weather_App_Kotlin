package com.example.weatherappkotlin

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherappkotlin.business.model.DailyWeatherModel
import com.example.weatherappkotlin.business.model.HourlyWeatherModel
import com.example.weatherappkotlin.business.model.Weather
import com.example.weatherappkotlin.business.model.WeatherDataModel
import com.example.weatherappkotlin.presentor.MainPresenter
import com.example.weatherappkotlin.view.*
import com.example.weatherappkotlin.view.adapters.MainDailyListAdapter
import com.example.weatherappkotlin.view.adapters.MainHourlyListAdapter
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationRequest.create
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class MainActivity : MvpAppCompatActivity(), MainView {

    private val mainPresenter by moxyPresenter { MainPresenter() }

    private val geoService by lazy { LocationServices.getFusedLocationProviderClient(this) }
    private val locationRequest by lazy { initLocationRequest() }
    private lateinit var mLocation: Location

    @SuppressLint("SetTextI18n", "MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        mainPresenter.refresh("33.44","-94.04")

        main_hour_list.apply {
            adapter = MainHourlyListAdapter()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }

        main_day_list.adapter = MainDailyListAdapter()
        main_day_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        main_day_list.setHasFixedSize(true)


        mainPresenter.enable()
        geoService.requestLocationUpdates(locationRequest, geoCallback, mainLooper)


    }

    private fun initViews() {
        main_city_name_tv.text = "Moscow"
        main_date_tv.text = "03 March"
        main_temp.text = "25\u00B0"
        main_temp_min_tv.text = "19"
        main_temp_max_tv.text = "19"
        main_weather_image.setImageResource(R.mipmap.cloud1x)
        main_pressue_mu_tv.text = "1023 hPa"
        main_humidity_mu_tv.text = "88 %"
        main_wind_speed_mu_tv.text = "5 m/s"
        main_sunrise_mu_tv.text = "4:30"
        main_sunset_mu_tv.text = "22:30"
    }


    // --------- Location Code ---------

    private fun initLocationRequest(): LocationRequest {
        val request: LocationRequest = LocationRequest.create()

        return request.apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    private val geoCallback = object : LocationCallback() {
        override fun onLocationResult(geo: LocationResult) {
            for (location in geo.locations) {
                mLocation = location
                mainPresenter.refresh(mLocation.longitude.toString(), mLocation.latitude.toString())
                Log.e("123", "onLocationResult: ${mLocation.latitude} ")
            }
        }
    }

    // ---------- initial activity code ----------


    // --------- Location Code ---------

    // ----- Moxy -----
    override fun displayLocation(data: String) {
        main_city_name_tv.text = data
    }

    @SuppressLint("SetTextI18n")
    override fun displayCurrentData(data: WeatherDataModel) {
        data.apply {
            main_date_tv.text =current.dt.toDateFormatOf(DAY_FULL_MONTH_NAME)
            main_temp.text = current.temp.toDegree() + "°"

           daily[0].temp.apply {
               main_temp_min_tv.text = min.toDegree()
               main_temp_max_tv.text = max.toDegree()
           }

            main_weather_image.setImageResource(R.mipmap.cloud1x)
            main_pressue_mu_tv.text = StringBuilder().append(current.pressure.toString()).append("hPa").toString()
            main_humidity_mu_tv.text = StringBuilder().append(current.humidity.toString()).append("%").toString()
            main_wind_speed_mu_tv.text = StringBuilder().append(current.humidity.toString()).append("m/s").toString()
            main_sunrise_mu_tv.text = current.sunrise.toDateFormatOf(HOUR_DOUBLE_DOT_MINUTE)
            main_sunset_mu_tv.text = current.sunrise.toDateFormatOf(HOUR_DOUBLE_DOT_MINUTE)
        }
    }

    override fun displayHourlyData(data: List<HourlyWeatherModel>) {
        (main_hour_list.adapter as MainHourlyListAdapter).updateData(data)

    }

    override fun displayDailyData(data: List<DailyWeatherModel>) {
        (main_day_list.adapter as MainDailyListAdapter).updateData(data)
    }

    override fun displayError(error: Throwable) {

    }

    override fun setLoading(flag: Boolean) {

    }

    // ----- Moxy -----
}
