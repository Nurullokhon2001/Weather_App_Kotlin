package com.example.weatherappkotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherappkotlin.business.model.DailyWeatherModel
import com.example.weatherappkotlin.view.adapters.MainDailyListAdapter
import com.example.weatherappkotlin.view.adapters.MainHourlyListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_daily_list.*

class DailyListFragment : DailyBaseFragment<List<DailyWeatherModel>>() {

    private val dailyAdapter = MainDailyListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_daily_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        daily_list.apply {
            adapter = dailyAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            dailyAdapter.clickListener = clickListener
            isNestedScrollingEnabled = true
        }

        mData?.let {
            updateView()
        }
    }


    override fun updateView() {
        dailyAdapter.updateData(mData!!,requireContext())
    }

    private val clickListener = object : MainDailyListAdapter.DayItemClick {
        override fun showDetails(data: DailyWeatherModel) {
            val fragment = DailyInfoFragment()
            fragment.setData(data)
            fm.beginTransaction().replace(R.id.fm_container, fragment).addToBackStack(null).commit()
//            Toast.makeText(requireContext(), "123", Toast.LENGTH_SHORT).show()
        }

    }

}