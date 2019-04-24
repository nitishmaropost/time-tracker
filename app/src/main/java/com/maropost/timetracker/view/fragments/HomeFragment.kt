package com.maropost.timetracker.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maropost.timetracker.R

class HomeFragment : MPBaseFragment() {

    private var mView : View?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(mView == null)
            mView = inflater.inflate(R.layout.home_fragment, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNavigationDrawer(true)
        showToolbar(true)
        setTitle(resources.getString(R.string.app_name))
    }
}