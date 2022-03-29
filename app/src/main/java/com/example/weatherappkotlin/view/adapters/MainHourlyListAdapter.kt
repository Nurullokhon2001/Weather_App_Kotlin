package com.example.weatherappkotlin.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.weatherappkotlin.R
import com.example.weatherappkotlin.business.model.HourlyWeatherModel
import com.google.android.material.textview.MaterialTextView

const val TAG = "RV_TEST"

class MainHourlyListAdapter : BaseAdapter<HourlyWeatherModel>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(TAG, "-----onCreateViewHolder: ")
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_main_hourly, parent, false)

        return ViewHolder(view)
    }


    inner class ViewHolder(view: View) : BaseViewHolder(view) {


 //       @BindView(R.id.item_hourly_time_tv)
        lateinit var time: MaterialTextView

 //       @BindView(R.id.item_hourly_temp_tv)
        lateinit var temperature: MaterialTextView

//        @BindView(R.id.item_hourly_pop_tv)
        lateinit var popBate: MaterialTextView

//        @BindView(R.id.item_hourly_weather_conditions_icon)
        lateinit var icon: ImageView

        init {
//            ButterKnife.bind(this, itemView)

            time = itemView.findViewById<MaterialTextView>(R.id.item_hourly_time_tv)
            temperature = itemView.findViewById<MaterialTextView>(R.id.item_hourly_temp_tv)
            popBate = itemView.findViewById<MaterialTextView>(R.id.item_hourly_pop_tv)
            icon = itemView.findViewById<ImageView>(R.id.item_hourly_weather_conditions_icon)
        }

        override fun bindView(position: Int) {
            time.text = "14:00"
            temperature.text = "14 \u00B0"
            popBate.text = "100%"
            icon.setImageResource(R.drawable.ic_sun)
        }
    }
}