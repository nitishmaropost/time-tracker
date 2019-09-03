package com.maropost.commons.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.maropost.timetracker.R
import com.ramotion.circlemenu.CircleMenuView
import kotlinx.android.synthetic.main.fragment_ramotion.view.*

class RamotionFragment : Fragment() {
    private var mView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        if (mView == null)
            mView = inflater.inflate(R.layout.fragment_ramotion, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        drawRamotionCircle()
    }

    fun drawRamotionCircle(){
        view?.circle_menu?.eventListener


    }
}

