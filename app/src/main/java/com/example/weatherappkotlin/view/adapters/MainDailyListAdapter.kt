package com.example.weatherappkotlin.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.weatherappkotlin.R
import com.example.weatherappkotlin.business.model.DailyWeatherModel
import com.google.android.material.textview.MaterialTextView

class MainDailyListAdapter() : BaseAdapter<DailyWeatherModel>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_main_daily, parent, false)

        return ViewHolder(view)
    }


    inner class ViewHolder(view: View) : BaseViewHolder(view) {


        @BindView(R.id.item_daily_date_tv)
        lateinit var date: MaterialTextView

        @BindView(R.id.item_min_temp_tv)
        lateinit var min: MaterialTextView

        @BindView(R.id.item_max_temp_tv)
        lateinit var max: MaterialTextView

        @BindView(R.id.item_daily_weather_conditions_icon)
        lateinit var icon: ImageView

        @BindView(R.id.item_pop_temp_tv)
        lateinit var pop: MaterialTextView

        init {
            ButterKnife.bind(this, itemView)
        }

        override fun bindView(position: Int) {
            date.text = "1 May"
            min.text = "14 \u00B0"
            max.text = "14 °"
            pop.text = "14 °"
            icon.setImageResource(R.drawable.ic_sun)
        }
    }
}