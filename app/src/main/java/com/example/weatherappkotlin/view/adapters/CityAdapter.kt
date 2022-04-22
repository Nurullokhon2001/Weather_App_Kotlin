package com.example.weatherappkotlin.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherappkotlin.R
import com.example.weatherappkotlin.business.model.GeoCodeModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView

class CityAdapter : BaseAdapter<GeoCodeModel>() {

    lateinit var clickListener: SearchItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_city_lists, parent, false)
        return CitySearchViewHolder(view)
    }

    interface SearchItemClickListener {
        fun addToFavorite(item: GeoCodeModel)

        fun removeFromFavorite(item: GeoCodeModel)

        fun showWeatherIn(item: GeoCodeModel)
    }

    inner class CitySearchViewHolder(view: View) : BaseViewHolder(view) {

        private var mCity: MaterialTextView = itemView.findViewById(R.id.search_city)

        private var mState: MaterialTextView = itemView.findViewById(R.id.state)

        private var mLocation: MaterialCardView = itemView.findViewById(R.id.location)


        private var mCountry: MaterialTextView = itemView.findViewById(R.id.search_country)

        private var mFavorite: MaterialButton = itemView.findViewById(R.id.favorite)

        override fun bindView(position: Int) {

            mLocation.setOnClickListener {
                clickListener.showWeatherIn(mData[position])
            }

            mFavorite.setOnClickListener {
                val item = mData[position]

                when ((it as MaterialButton).isChecked) {
                    true -> {
                        item.isFavorite = true
                        clickListener.addToFavorite(item)
                    }
                    false -> {
                        item.isFavorite = false
                        clickListener.removeFromFavorite(item)
                    }
                }
            }

            mData[position].apply {
                mState.text = if (!state.isNullOrEmpty()) "\",  \" + $state\"" else ""
                mCity.text = local_names.toString()
                mCountry.text = country
                mFavorite.isChecked = isFavorite
            }

        }

    }
}