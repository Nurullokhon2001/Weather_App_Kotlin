package com.example.weatherappkotlin.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.weatherappkotlin.R
import com.example.weatherappkotlin.business.model.DailyWeatherModel
import com.example.weatherappkotlin.view.DAY_WEEK_NAME_LONG
import com.example.weatherappkotlin.view.toDateFormatOf
import com.example.weatherappkotlin.view.toDegree
import com.example.weatherappkotlin.view.toPercentString
import com.google.android.material.textview.MaterialTextView


class MainDailyListAdapter : BaseAdapter<DailyWeatherModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_main_daily, parent, false)

        return ViewHolder(view)
    }


    inner class ViewHolder(view: View) : BaseViewHolder(view) {


        private  var date: MaterialTextView = itemView.findViewById(R.id.item_daily_date_tv)

        private var min: MaterialTextView = itemView.findViewById(R.id.item_min_temp_tv)

        private    var max: MaterialTextView = itemView.findViewById(R.id.item_max_temp_tv)

        private  var icon: ImageView = itemView.findViewById(R.id.item_daily_weather_conditions_icon)

        private var pop: MaterialTextView = itemView.findViewById(R.id.item_pop_temp_tv)

        override fun bindView(position: Int) {
            mData[position].apply {
                date.text = dt.toDateFormatOf(DAY_WEEK_NAME_LONG)
                min.text =
                    StringBuilder().append(temp.min.toDegree()).append("°").toString()
                max.text =
                    StringBuilder().append(temp.max.toDegree()).append("°").toString()
                pop.text = popp.toPercentString(" %")

                Glide.with(context)
                    .load("https://openweathermap.org/img/wn/" + weather[0].icon + "@2x.png")
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .skipMemoryCache(true)
                    .into(icon)
            }

        }
    }
}