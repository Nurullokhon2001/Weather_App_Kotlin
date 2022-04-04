package com.example.weatherappkotlin.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
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
                date.text = dt.toDateFormatOf("dd EEEE")
                min.text =
                    StringBuilder().append(temp.min.toDegree()).append("°").toString()
                max.text =
                    StringBuilder().append(temp.max.toDegree()).append("°").toString()
                pop.text = popp.toPercentString(" %")

                Glide.with(context)
                    .load("https://openweathermap.org/img/wn/" + weather[0].icon + "@2x.png")
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(icon);
            }

        }
    }

    fun Long.toDateFormatOf(format: String): String {
        val cal = Calendar.getInstance()
        val timeZone = cal.timeZone
        val sdf = SimpleDateFormat(format, Locale.ENGLISH)
        sdf.timeZone = timeZone
        return sdf.format(Date(this * 1000))
    }
    fun Double.toPercentString(extraPart: String = "") =
        (this + 100).roundToInt().toString() + extraPart

    fun Double.toDegree() = (this - 273.15).roundToInt().toString()
}