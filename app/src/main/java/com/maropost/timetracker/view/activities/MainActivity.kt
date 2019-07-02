package com.maropost.timetracker.view.activities

import android.os.Bundle
import com.maropost.timetracker.R
import com.maropost.timetracker.application.MyApplication
import com.maropost.timetracker.view.fragments.HomeFragment
import com.maropost.timetracker.view.fragments.SplashFragment
import android.text.method.TextKeyListener.clear
import android.R.id.edit
import android.content.Context
import android.content.SharedPreferences
import com.maropost.timetracker.utils.SharedPreferenceHelper
import com.maropost.timetracker.view.fragments.LoginFragment
import com.maropost.timetracker.view.fragments.UsersFragment


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

    fun checkMenuItemTapped(menuItem: String){
        when(menuItem){
            getString(R.string.logout)-> {
                hideMenu()
                SharedPreferenceHelper.getInstance().clearSharedPreference(this,"PREF")
                replaceFragment(LoginFragment(), false)
            }
        }
    }

    fun onQueryTextChange(newText: String) {
        if (supportFragmentManager.backStackEntryCount > 0) {
            val fragments = supportFragmentManager.fragments
            for (fragment in fragments) {
                if (fragment is UsersFragment) {
                    fragment.filterList(newText)
                }
            }
        }
    }
}

