package com.maropost.timetracker.view.activities

import android.os.Bundle
import com.maropost.timetracker.application.MyApplication
import com.maropost.timetracker.view.fragments.HomeFragment
import com.maropost.timetracker.view.fragments.LoginFragment


class MainActivity : MPBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchInitialView()
        MyApplication.getInstance().setMainActivityInstance(this)
    }

    /**
     * Display the initial splash view
     */
    private fun launchInitialView() {
        //replaceFragment(SplashFragment(),false)
        replaceFragment(HomeFragment(),true)
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
}

