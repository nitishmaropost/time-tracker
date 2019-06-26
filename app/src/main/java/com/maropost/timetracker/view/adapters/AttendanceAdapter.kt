package com.maropost.timetracker.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.maropost.timetracker.R
import com.maropost.timetracker.pojomodels.Attendance
import kotlinx.android.synthetic.main.item_attendance_fragment.view.*
import java.util.*

class AttendanceAdapter (private var arrayList: ArrayList<Attendance>, val context: Context) : RecyclerView.Adapter<AttendanceViewHolder>() {

    private var attendance: Attendance? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceViewHolder {
        return AttendanceViewHolder(LayoutInflater.from(context).inflate(R.layout.item_attendance_fragment, parent, false))
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: AttendanceViewHolder, position: Int) {

       /* if(attendance != null) {
            holder.txtPunchType?.text = attendance!!.pinTypeTextMap?.get(arrayList[position].pin_type.toString())
            when {
                holder.txtPunchType?.text!!.contains("In") -> holder.txtPunchType?.setTextColor(ContextCompat.getColor(context, R.color.colorGreen_900))
                holder.txtPunchType?.text!!.contains("Out") -> holder.txtPunchType?.setTextColor(ContextCompat.getColor(context, R.color.colorRed))
                else -> holder.txtPunchType?.setTextColor(ContextCompat.getColor(context, R.color.colorBlack))
            }
        }*/


    }

    /**
     * Set the model framed from the response obtained from Gson
     */
    fun setModel(attendance: Attendance) {
        this.attendance = attendance
    }
}

class AttendanceViewHolder(view: View): RecyclerView.ViewHolder(view) {
    var txtEmpName: TextView? = view.txtEmpName
    var txtPosition: TextView? = view.txtPosition
    var txtDepartment : TextView? = view.txtDepartment
    var txtEmpCode : TextView? = view.txtEmpCode
}