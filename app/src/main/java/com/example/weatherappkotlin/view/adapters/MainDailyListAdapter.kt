package com.example.weatherappkotlin.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.weatherappkotlin.R
import com.example.weatherappkotlin.business.model.DailyWeatherModel
import com.google.android.material.textview.MaterialTextView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class MainDailyListAdapter() : BaseAdapter<DailyWeatherModel>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_main_daily, parent, false)

        return ViewHolder(view)
    }


    inner class ViewHolder(view: View) : BaseViewHolder(view) {


        //      @BindView(R.id.item_daily_date_tv)
        lateinit var date: MaterialTextView

        //       @BindView(R.id.item_min_temp_tv)
        lateinit var min: MaterialTextView

        //     @BindView(R.id.item_max_temp_tv)
        lateinit var max: MaterialTextView

        //        @BindView(R.id.item_daily_weather_conditions_icon)
        lateinit var icon: ImageView

        //     @BindView(R.id.item_pop_temp_tv)
        lateinit var pop: MaterialTextView

        init {
            //           ButterKnife.bind(this, itemView)
            date = itemView.findViewById<MaterialTextView>(R.id.item_daily_date_tv)
            min = itemView.findViewById<MaterialTextView>(R.id.item_min_temp_tv)
            max = itemView.findViewById<MaterialTextView>(R.id.item_max_temp_tv)
            pop = itemView.findViewById<MaterialTextView>(R.id.item_pop_temp_tv)
            icon = itemView.findViewById<ImageView>(R.id.item_daily_weather_conditions_icon)
        }

        override fun bindView(position: Int) {
            mData[position].apply {

                val cal = Calendar.getInstance()
                val timeZone = cal.timeZone
                val sdf = SimpleDateFormat("\"HH:mm\"", Locale.getDefault())
                sdf.timeZone = timeZone
                date.text = (sdf.format(Date(dt * 1000)))

                min.text =
                    StringBuilder().append((temp.min - 273.15).roundToInt().toString()).append(" °")
                        .toString()
                max.text =
                    StringBuilder().append((temp.max - 273.15).roundToInt().toString()).append(" °")
                        .toString()
                pop.text = popp.toString()
                icon.setImageResource(R.drawable.ic_sun)
            }

        }
    }
}