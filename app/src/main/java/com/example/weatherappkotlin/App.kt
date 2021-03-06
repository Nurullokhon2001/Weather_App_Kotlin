package com.example.weatherappkotlin

import android.app.Application
import android.content.Intent
import androidx.room.Room
import com.example.weatherappkotlin.business.room.OpenWeatherDB

const val APP_SETTINGS = "App Settings"
const val IS_STARTED_APP = "Is started app"

class App : Application() {

    companion object {
        var db: OpenWeatherDB? = null
    }

    override fun onCreate() {
        super.onCreate()


        // TODO убрать fallbackToDestructiveMigration к релизу
        if (db == null) {
            db = Room.databaseBuilder(this, OpenWeatherDB::class.java, "weatherdb")
                .fallbackToDestructiveMigration().build()
        }


        val preferences = getSharedPreferences(APP_SETTINGS, MODE_PRIVATE)

        val flag = preferences.getBoolean(IS_STARTED_APP, false)

        if (!flag) {
            val editor = preferences.edit()
            editor.putBoolean(IS_STARTED_APP, true)
            editor.apply()
            val intent = Intent(this, InitialActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

    }

}