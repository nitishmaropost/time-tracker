package com.maropost.timetracker.view.adapters

import android.annotation.SuppressLint
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

class AttendanceDetailAdapter(private var arrayList: ArrayList<Rows>, val context: Context) : RecyclerView.Adapter<AttendanceDetailViewHolder>() {

    private var formatter :  SimpleDateFormat ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceDetailViewHolder {
        return AttendanceDetailViewHolder(LayoutInflater.from(context).inflate(R.layout.item_attendance_detail, parent, false))
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: AttendanceDetailViewHolder, position: Int) {

        if(formatter == null)
            formatter = SimpleDateFormat("dd/MM/yyyy")
        val stringTokenizer = StringTokenizer(Utility.getInstance().getEpochTime(arrayList[position].punch_time))

        val strDate = stringTokenizer.nextToken() // Extract only date string
        val strTime = stringTokenizer.nextToken() // Extract only time string
        val strAmOrPm = stringTokenizer.nextToken() // Extract AM/PM

        if(position == 0) {
            holder.txtDateHeader?.visibility = View.VISIBLE
            holder.txtDateHeader?.text = strDate
        }
        else{
            // Convert date string to date object
            val itemDate = formatter!!.parse(strDate)

            // Extract only date string for previous entry
            val strPreviousDate = StringTokenizer(Utility.getInstance()
                .getEpochTime(arrayList[position-1].punch_time))
                .nextToken()

            // Convert previous date string to date object
            val previousItemDate = formatter!!.parse(strPreviousDate)

            // Compare the two dates - if the Current Item date < Previous date, then display header else remove it
            if (previousItemDate.after(itemDate)) {
                holder.txtDateHeader?.visibility = View.VISIBLE
                holder.txtDateHeader?.text = strDate
            }
            else if(previousItemDate.equals(itemDate))
                holder.txtDateHeader?.visibility = View.GONE
        }

        holder.txtTimeOfPunch?.text = strTime + " " + strAmOrPm.toUpperCase()

        when(arrayList[position].pin_type){
            1   -> holder.txtPunchType?.text = context.getString(R.string.in_fingerprint)
            4   -> holder.txtPunchType?.text = context.getString(R.string.in_card)
            101 -> holder.txtPunchType?.text = context.getString(R.string.out_fingerprint)
            104 -> holder.txtPunchType?.text = context.getString(R.string.out_card)
        }
    }
}

class AttendanceDetailViewHolder(view: View): RecyclerView.ViewHolder(view) {
    var txtTimeOfPunch: TextView? = view.txtTimeOfPunch
    var txtPunchType: TextView? = view.txtPunchType
    var txtDateHeader : TextView? = view.txtDateHeader
}