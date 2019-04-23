package com.maropost.timetracker.view.activities

import android.os.Bundle
import com.maropost.timetracker.view.fragments.SplashFragment

class MainActivity : MPBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchInitialView()
    }

    /**
     * Display the initial splash view
     */
    private fun launchInitialView() {
        replaceFragment(SplashFragment(),false)
    }
}

