package com.maropost.timetracker.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maropost.commons.fragments.MPBaseFragment
import com.maropost.timetracker.R
import kotlinx.android.synthetic.main.register_fragment.*

class RegisterFragment : MPBaseFragment() {

    private var mView : View?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(mView == null)
            mView = inflater.inflate(R.layout.register_fragment, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lockNavigationDrawer(false)
        showToolbar(false)
        initialiseListeners()
    }

    private fun initialiseListeners() {
        txtLogin.setOnClickListener{
            popCurrentFragment()
        }
        relDone.setOnClickListener{
           // popAllFragments()
            replaceFragment(HomeFragment(),true)

        }
    }
}