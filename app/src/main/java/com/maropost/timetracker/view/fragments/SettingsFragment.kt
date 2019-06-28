package com.maropost.timetracker.view.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.maropost.timetracker.R
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.home_fragment.*

class SettingsFragment : MPBaseFragment() {
    private var mView : View?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(mView == null)
            mView = inflater.inflate(R.layout.fragment_settings, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialiseListener()
    }

    /**
     * Setup listeners
     */
    private fun initialiseListener() {
        btnChangePassword.setOnClickListener{
            replaceFragment(ChangePasswordFragment(),true)
        }

    }

}
