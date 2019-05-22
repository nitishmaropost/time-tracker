package com.maropost.timetracker.view.fragments

import android.support.v4.app.Fragment
import android.view.View
import com.maropost.timetracker.view.activities.MPBaseActivity
import kotlinx.android.synthetic.main.app_bar_main.*

open class MPBaseFragment : Fragment() {

    /**
     * Replace current fragment with new one
     * addToBackStack - true/false keep it in back stack or not
     */
    fun replaceFragment(fragment: MPBaseFragment,addToBackStack: Boolean) {
            (activity as MPBaseActivity).replaceFragment(fragment,addToBackStack)
    }

    /**
     * Replace current fragment with new one along with Shared Element Transaction
     * addToBackStack - true/false keep it in back stack or not
     */
    fun replaceFragment(fragment: Fragment,addToBackStack: Boolean,view: View) {
        (activity as MPBaseActivity).replaceFragment(fragment,addToBackStack,view)
    }

    fun setToolbarIconVisibility(allow: Boolean){
        (activity as MPBaseActivity).setToolbarIconVisibility(allow)
    }

    /**
     * Display a snack message
     */
    fun showSnackAlert(message: String?) {
        if (activity as MPBaseActivity? == null || message == null) {
            return
        }
        (activity as MPBaseActivity).showSnackAlert(message)
    }

    /**
     * Display permission denied alert
     */
    fun showPermissionSnackAlert(){
        (activity as MPBaseActivity).showPermissionSnackAlert()
    }

    /**
     * Pop current displaying fragment
     */
    fun popCurrentFragment() {
        try {
            (activity as MPBaseActivity).popCurrentFragment()
        } catch (e:Exception) {
          e.printStackTrace()
        }
    }


    /**
     * Pop all fragments from stack
     */
    fun popAllFragments() {
        (activity as MPBaseActivity).popAllFragments()
    }

    /**
     * Remove fragment with tag in back stack
     */
    fun popFragmentByTag(tag:String){
        try {
            (activity as MPBaseActivity).popFragmentByTag(tag)
        } catch (e:Exception) {
            e.printStackTrace()
        }
    }


    /**
     * Control navigation drawer visibility
     */
    fun showNavigationDrawer(allow:Boolean){
        (activity as MPBaseActivity).showNavigationDrawer(allow)
    }

    /**
     * Set toolbar visibility
     */
    fun showToolbar(allow: Boolean) {
        (activity as MPBaseActivity).showToolbar(allow)
    }

    /**
     * Set toolbar title
     */
    fun setTitle(title: String){
        (activity as MPBaseActivity).setTitle(title)
    }

    /**
     * Set progress bar display
     */
    fun showProgressBar(isAllow: Boolean){
        (activity as MPBaseActivity).showProgressBar(isAllow)
    }

}