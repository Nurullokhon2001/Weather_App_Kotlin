package com.example.weatherappkotlin.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.weatherappkotlin.R
import com.example.weatherappkotlin.business.model.HourlyWeatherModel
import com.example.weatherappkotlin.view.HOUR_DOUBLE_DOT_MINUTE
import com.example.weatherappkotlin.view.toDateFormatOf
import com.example.weatherappkotlin.view.toDegree
import com.example.weatherappkotlin.view.toPercentString
import com.google.android.material.textview.MaterialTextView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

const val TAG = "RV_TEST"

class MainHourlyListAdapter : BaseAdapter<HourlyWeatherModel>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(TAG, "-----onCreateViewHolder: ")
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_main_hourly, parent, false)

        return ViewHolder(view)
    }


    inner class ViewHolder(view: View) : BaseViewHolder(view) {


         private var time: MaterialTextView = itemView.findViewById(R.id.item_hourly_time_tv)

        private var temperature: MaterialTextView = itemView.findViewById(R.id.item_hourly_temp_tv)

        private var popBate: MaterialTextView = itemView.findViewById(R.id.item_hourly_pop_tv)

        private   var icon: ImageView = itemView.findViewById(R.id.item_hourly_weather_conditions_icon)

        override fun bindView(position: Int) {
            mData[position].apply {

                time.text = dt.toDateFormatOf(HOUR_DOUBLE_DOT_MINUTE)
                temperature.text = StringBuilder().append(temp.toDegree()).append(" °").toString()
                popBate.text = pop.toPercentString(" %")
//01d
                Glide.with(context)
                    .load("https://openweathermap.org/img/wn/" + weather[0].icon + "@2x.png")
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .skipMemoryCache(true)
                    .into(icon)
            }

        }
    }

}