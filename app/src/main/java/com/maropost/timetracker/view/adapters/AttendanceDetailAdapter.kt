package com.maropost.timetracker.view.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.maropost.timetracker.R
import com.maropost.timetracker.pojomodels.AttendanceDetailsPojo
import com.maropost.timetracker.pojomodels.Rows
import kotlinx.android.synthetic.main.item_attendance_detail.view.*
import java.util.ArrayList

class AttendanceDetailAdapter(private var arrayList: ArrayList<Rows>, val context: Context) : RecyclerView.Adapter<AttendanceDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceDetailViewHolder {
        return AttendanceDetailViewHolder(LayoutInflater.from(context).inflate(R.layout.item_attendance_detail, parent, false))
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: AttendanceDetailViewHolder, position: Int) {
        holder.textEmpCode?.text= "10:05 AM"
        holder.txtTimeOfPunch?.text= "07:10 PM"
    }
}

class AttendanceDetailViewHolder(view: View): RecyclerView.ViewHolder(view) {
    var textEmpCode: TextView? = view.textEmpCode
    var txtTimeOfPunch: TextView? = view.txtTimeOfPunch
    var txtPunchType: TextView? = view.txtPunchType
}