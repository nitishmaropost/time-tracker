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
import com.maropost.timetracker.utils.Utility
import kotlinx.android.synthetic.main.item_attendance_detail.view.*
import java.text.SimpleDateFormat
import java.util.*


class AttendanceDetailAdapter(private var arrayList: ArrayList<Rows>, val context: Context) :
    RecyclerView.Adapter<AttendanceDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceDetailViewHolder {
        return AttendanceDetailViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_attendance_detail,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: AttendanceDetailViewHolder, position: Int) {
        if (position == 0)
            holder.txtTimeOfPunch?.text = Utility.getInstance().getEpochTime(arrayList[position].punch_time)
        else {
            val dateNow = Utility.getInstance().getEpochDate(arrayList[position].punch_time)
            val dateBefore = Utility.getInstance().getEpochDate(arrayList[position - 1].punch_time)
            if (dateNow.after(dateBefore)) {
                holder.txtDateHeader?.visibility = View.VISIBLE
                holder.txtDateHeader?.text = dateNow.toString()
                holder.txtTimeOfPunch?.text = Utility.getInstance().getEpochTime(arrayList[position].punch_time)
            }
            else
                holder.txtTimeOfPunch?.text=Utility.getInstance().getEpochTime(arrayList[position].punch_time)
        }





        when (arrayList[position].pin_type) {
            1 -> holder.txtPunchType?.text = context.getString(R.string.in_fingerprint)
            4 -> holder.txtPunchType?.text = context.getString(R.string.in_card)
            101 -> holder.txtPunchType?.text = context.getString(R.string.out_fingerprint)
            104 -> holder.txtPunchType?.text = context.getString(R.string.out_card)
        }
    }
}

class AttendanceDetailViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var txtTimeOfPunch: TextView? = view.txtTimeOfPunch
    var txtDateHeader: TextView? = view.txtDateHeader
    var txtPunchType: TextView? = view.txtPunchType
}