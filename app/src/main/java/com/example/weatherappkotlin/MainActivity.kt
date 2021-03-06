package com.example.weatherappkotlin

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Point
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.PointerIcon
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.weatherappkotlin.business.model.DailyWeatherModel
import com.example.weatherappkotlin.business.model.HourlyWeatherModel
import com.example.weatherappkotlin.business.model.WeatherDataModel
import com.example.weatherappkotlin.presentor.MainPresenter
import com.example.weatherappkotlin.view.*
import com.example.weatherappkotlin.view.adapters.MainDailyListAdapter
import com.example.weatherappkotlin.view.adapters.MainHourlyListAdapter
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_menu.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class MainActivity : MvpAppCompatActivity(), MainView {

    private val mainPresenter by moxyPresenter { MainPresenter() }

    private val geoService by lazy { LocationServices.getFusedLocationProviderClient(this) }
    private val locationRequest by lazy { initLocationRequest() }
    private lateinit var mLocation: Location

    @SuppressLint("SetTextI18n", "MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBottomSheets()
        supportFragmentManager.beginTransaction()
            .add(R.id.fm_container, DailyListFragment(), DailyListFragment::class.simpleName)
            .commit()

        if (!intent.hasExtra("Coordinate")) {
            geoService.requestLocationUpdates(locationRequest, geoCallback, mainLooper)
        } else {
            val coord = intent.extras!!.getBundle("Coordinate")!!
            val loc = Location("")
            loc.latitude = coord.getString("lat")!!.toDouble()
            loc.longitude = coord.getString("lon")!!.toDouble()
            mLocation = loc
            mainPresenter.refresh(
                lat = mLocation.latitude.toString(),
                long = mLocation.longitude.toString()
            )
        }

        main_menu_btn.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, R.anim.slide_out_left)
        }

        main_hour_list.apply {
            adapter = MainHourlyListAdapter()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }

        mainPresenter.enable()

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
                mainPresenter.refresh(mLocation.latitude.toString(), mLocation.longitude.toString())
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
            main_date_tv.text = current.dt.toDateFormatOf(DAY_FULL_MONTH_NAME)
            main_temp.text = StringBuilder().append(current.temp.toDegree()).append("??").toString()
            main_weather_tv.text = current.weather[0].description
            daily[0].temp.apply {
                main_temp_min_tv.text = min.toDegree()
                main_temp_max_tv.text = max.toDegree()
            }

            main_weather_image.setImageResource(R.mipmap.cloud1x)
            main_pressue_mu_tv.text =
                StringBuilder().append(current.pressure.toString()).append(" hPa").toString()
            main_humidity_mu_tv.text =
                StringBuilder().append(current.humidity.toString()).append("%").toString()
            main_wind_speed_mu_tv.text =
                StringBuilder().append(current.humidity.toString()).append("m/s").toString()
            main_sunrise_mu_tv.text = current.sunrise.toDateFormatOf(HOUR_DOUBLE_DOT_MINUTE)
            main_sunset_mu_tv.text = current.sunset.toDateFormatOf(HOUR_DOUBLE_DOT_MINUTE)

            Glide.with(this@MainActivity)
                .load("https://openweathermap.org/img/wn/" + current.weather[0].icon + "@2x.png")
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .skipMemoryCache(true)
                .into(main_weather_conditions_icon)

        }
    }

    override fun displayHourlyData(data: List<HourlyWeatherModel>) {
        (main_hour_list.adapter as MainHourlyListAdapter).updateData(data, this)

    }

    override fun displayDailyData(data: List<DailyWeatherModel>) {
//        (main_day_list.adapter as MainDailyListAdapter).updateData(data, this)
        (supportFragmentManager.findFragmentByTag(DailyListFragment::class.simpleName) as DailyListFragment).setData(
            data
        )
    }

    override fun displayError(error: Throwable) {

    }

    override fun setLoading(flag: Boolean) {
        progress_circular.isActivated = flag
        progress_circular.visibility = if (flag) View.VISIBLE else View.GONE
        id1.visibility = if (!flag) View.VISIBLE else View.GONE
        id2.visibility = if (!flag) View.VISIBLE else View.GONE
        id3.visibility = if (!flag) View.VISIBLE else View.GONE
        id4.visibility = if (!flag) View.VISIBLE else View.GONE
        id5.visibility = if (!flag) View.VISIBLE else View.GONE
        id6.visibility = if (!flag) View.VISIBLE else View.GONE
        id7.visibility = if (!flag) View.VISIBLE else View.GONE
        id8.visibility = if (!flag) View.VISIBLE else View.GONE
    }

    // ----- Moxy -----

    private fun initBottomSheets() {
        main_bottom_sheets.isNestedScrollingEnabled = true
        val size = Point()
        windowManager.defaultDisplay.getSize(size)
        main_bottom_sheets_container.layoutParams =
            CoordinatorLayout.LayoutParams(size.x, (size.y * 0.6).roundToInt())
    }


}
