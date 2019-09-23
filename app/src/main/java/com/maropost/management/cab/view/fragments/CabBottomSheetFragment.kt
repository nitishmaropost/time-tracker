package com.maropost.management.cab.view.fragments

import android.app.Dialog
import android.view.LayoutInflater
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.maropost.management.R

class CabBottomSheetFragment: BottomSheetDialogFragment() {

    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_layout, null);
        dialog?.setContentView(view)
    }


}