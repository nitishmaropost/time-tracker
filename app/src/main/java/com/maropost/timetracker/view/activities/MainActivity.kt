package com.maropost.timetracker.view.activities

import android.os.Bundle
import com.maropost.timetracker.application.MyApplication
import com.maropost.timetracker.view.fragments.HomeFragment
import com.maropost.timetracker.view.fragments.SplashFragment


class MainActivity : MPBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchInitialView()
        MyApplication.getInstance().setMainActivityInstance(this)
    }

    /**
     * Display the initial splash view
     */
    fun launchInitialView() {
        replaceFragment(SplashFragment(),false)
        //replaceFragment(AttendanceDetailFragment(),true)
        //Log.e("awew","wqeqw")
    }

    /**
     * Fetch home fragment from backstack and refresh view for the date selected from picker
     */
    fun setCalenderDetails(year: Int, month: Int, day: Int) {
        if (supportFragmentManager.backStackEntryCount > 0) {
            val fragments = supportFragmentManager.fragments
                for (fragment in fragments) {
                    if (fragment is HomeFragment ){
                        fragment.setCalenderDetails(year,month,day)
                    }
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove all app instances from memory
        android.os.Process.killProcess(android.os.Process.myPid())
    }



}

