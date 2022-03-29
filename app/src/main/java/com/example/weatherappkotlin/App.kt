package com.example.weatherappkotlin

import android.app.Application
import android.content.Intent

const val APP_SETTINGS = "App_Settings"
const val IS_STARTED_APP = "Is started app"

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val preferences = getSharedPreferences(APP_SETTINGS, MODE_PRIVATE)

        val flag = preferences.contains(IS_STARTED_APP)

        if (!flag) {
            val editor = preferences.edit()
            editor.putBoolean(IS_STARTED_APP,true)
            val intent = Intent(this, InitialActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        } else {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}