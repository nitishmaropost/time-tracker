package com.maropost.management.commons.activities

import android.os.Bundle
import com.maropost.management.R
import com.maropost.management.cab.view.fragments.MapFragment
import com.maropost.management.commons.application.MyApplication
import com.maropost.management.commons.fragments.RamotionFragment
import com.maropost.management.commons.utils.SharedPreferenceHelper
import com.maropost.management.time.view.fragments.LoginFragment
import com.maropost.management.time.view.fragments.UsersFragment


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
        replaceFragment(MapFragment(),false)
        //replaceFragment(RamotionFragment(),false)
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
                lockNavigationMenu(true)
                SharedPreferenceHelper.getInstance().clearSharedPreference(this,"PREF")
                replaceFragment(LoginFragment(), false)
            }
            getString(R.string.attendance)-> {
                lockNavigationMenu(false)
                replaceFragment(UsersFragment(), true)
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

