package com.example.weatherappkotlin
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherappkotlin.business.model.DailyWeatherModel
import kotlinx.android.synthetic.main.fragment_daily_info.*

class DailyInfoFragment : DailyBaseFragment<DailyWeatherModel>() {

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

    }

    override fun updateView() {
        TODO("Not yet implemented")
    }


}