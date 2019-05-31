package com.maropost.timetracker.view.activities

import android.os.Bundle
import android.util.Log
import com.maropost.timetracker.application.MyApplication
import com.maropost.timetracker.view.fragments.AttendanceDetailFragment
import com.maropost.timetracker.view.fragments.HomeFragment
import com.maropost.timetracker.view.fragments.SplashFragment
import android.util.DisplayMetrics

class MainActivity : MPBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchInitialView()
        MyApplication.getInstance().setMainActivityInstance(this)

/*
        when (resources.displayMetrics.densityDpi) {
            DisplayMetrics.DENSITY_LOW -> {

            }

            DisplayMetrics.DENSITY_MEDIUM -> {
            }

            DisplayMetrics.DENSITY_TV, DisplayMetrics.DENSITY_HIGH -> {
            }

            DisplayMetrics.DENSITY_XHIGH, DisplayMetrics.DENSITY_280 -> {
            }

            DisplayMetrics.DENSITY_XXHIGH, DisplayMetrics.DENSITY_360, DisplayMetrics.DENSITY_400, DisplayMetrics.DENSITY_420 -> {
            }

            DisplayMetrics.DENSITY_XXXHIGH, DisplayMetrics.DENSITY_560 -> {
            }
        }
*/
    }

    /**
     * Display the initial splash view
     */
    private fun launchInitialView() {
        replaceFragment(SplashFragment(),false)
        //replaceFragment(HomeFragment(),true)
    }

    /**
     * Fetch home fragment from backstack and refresh view for the date selected from picker
     */
   /* fun setCalenderDetails(year: Int, month: Int, day: Int) {
        if (supportFragmentManager.backStackEntryCount > 0) {
            val fragments = supportFragmentManager.fragments
                for (fragment in fragments) {
                    if (fragment is HomeFragment ){
                        fragment.setCalenderDetails(year,month,day)
                    }
                }
        }
    }*/

    override fun onDestroy() {
        super.onDestroy()
        // Remove all app instances from memory
        android.os.Process.killProcess(android.os.Process.myPid())
    }
}

