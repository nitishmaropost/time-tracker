package com.maropost.timetracker.application

import android.app.Application
import com.maropost.timetracker.view.activities.MainActivity

class MyApplication : Application() {

    private lateinit var actiity: MainActivity

    init {
        instance = this
    }

    companion object {
        private var instance: MyApplication? = null

        fun getInstance() : MyApplication {
            return instance!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        if(instance == null)
            instance = this
    }

    fun setMainActivityInstance(actiity: MainActivity){
        this.actiity = actiity
    }

    fun setCalenderDetails(year: Int, month: Int, day: Int) {
        actiity.setCalenderDetails(year,month,day)
    }
}