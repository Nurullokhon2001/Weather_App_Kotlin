package com.example.weatherappkotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.SurfaceControl
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.weatherappkotlin.business.model.DailyWeatherModel
import com.example.weatherappkotlin.view.DAY_FULL_MONTH_NAME
import com.example.weatherappkotlin.view.HOUR_DOUBLE_DOT_MINUTE
import com.example.weatherappkotlin.view.toDateFormatOf
import com.example.weatherappkotlin.view.toDegree
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_daily_info.*

class DailyInfoFragment : DailyBaseFragment<DailyWeatherModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val inflater = TransitionInflater.from(requireContext())

        exitTransition = inflater.inflateTransition(R.transition.slide_right)
        enterTransition = inflater.inflateTransition(R.transition.slide_out_right)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_daily_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_back.setOnClickListener {
            fm.popBackStack()
        }
        updateView()
    }

    @SuppressLint("SetTextI18n")
    override fun updateView() {
        mData?.apply {
            day_date.text = dt.toDateFormatOf(DAY_FULL_MONTH_NAME)
            day_temp.text = StringBuilder().append(temp.day.toDegree()).append("°").toString()
            Glide.with(requireContext())
                .load("https://openweathermap.org/img/wn/" + weather[0].icon + "@2x.png")
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .skipMemoryCache(true)
                .into(iv_icon)

            tv_morn_top.text = StringBuilder().append(temp.morn.toDegree()).append("°").toString()
            tv_morn_bottom.text =
                StringBuilder().append(feels_like.morn.toDegree()).append("°").toString()

            tv_day_top.text = StringBuilder().append(temp.day.toDegree()).append("°").toString()
            tv_day_bottom.text =
                StringBuilder().append(feels_like.day.toDegree()).append("°").toString()

            tv_eve_top.text = StringBuilder().append(temp.eve.toDegree()).append("°").toString()
            tv_eve_bottom.text =
                StringBuilder().append(feels_like.eve.toDegree()).append("°").toString()

            tv_night_top.text = StringBuilder().append(temp.night.toDegree()).append("°").toString()
            tv_night_bottom.text =
                StringBuilder().append(feels_like.night.toDegree()).append("°").toString()

            tv_humidity.text = "$humidity %"
            tv_pressure.text = "$pressure hPa"
            tv_wind.text = "$wind_speed m/s"
            tv_diration.text = "$wind_deg"
            tv_sunrise.text = sunrise.toDateFormatOf(HOUR_DOUBLE_DOT_MINUTE)
            tv_sunset.text = sunset.toDateFormatOf(HOUR_DOUBLE_DOT_MINUTE)

        }
    }


}