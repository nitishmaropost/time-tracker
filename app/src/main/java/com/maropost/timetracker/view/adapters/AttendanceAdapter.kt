package com.maropost.timetracker.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.maropost.timetracker.R
import com.maropost.timetracker.pojomodels.Attendance
import com.maropost.timetracker.pojomodels.RowShifts
import kotlinx.android.synthetic.main.item_attendance_fragment.view.*
import java.util.*
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.SpannableString
import android.graphics.Color


class AttendanceAdapter (private var arrayList: ArrayList<RowShifts>, val context: Context) : RecyclerView.Adapter<AttendanceViewHolder>() {

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

        holder.txtEmpName?.text = arrayList[position].user_data?.full_name
        holder.txtEmpCode?.text = "Emp Code: " + arrayList[position].user_data?.emp_code

        val email = arrayList[position].user_data?.email_id
        val builder = SpannableStringBuilder()

        builder.append("Email: ")

        val redSpannable = SpannableString(email)
        redSpannable.setSpan(ForegroundColorSpan(Color.BLUE), 0, email!!.length, 0)
        builder.append(redSpannable)
        //holder.txtEmail?.text = "Email: " + arrayList[position].user_data?.email_id
        holder.txtEmail?.setText(builder, TextView.BufferType.SPANNABLE)


        if(!TextUtils.isEmpty(arrayList[position].user_data?.position)) {
            holder.txtPosition?.text = arrayList[position].user_data?.position
            holder.txtPosition?.visibility = View.VISIBLE
        }else holder.txtPosition?.visibility = View.GONE

        if(!TextUtils.isEmpty(arrayList[position].user_data?.department)) {
            holder.txtDepartment?.text = "Dept: "+arrayList[position].user_data?.department
            holder.txtDepartment?.visibility = View.VISIBLE
        }else holder.txtDepartment?.visibility = View.GONE

    }
}

class AttendanceViewHolder(view: View): RecyclerView.ViewHolder(view) {
    var txtEmpName: TextView? = view.txtEmpName
    var txtPosition: TextView? = view.txtPosition
    var txtDepartment : TextView? = view.txtDepartment
    var txtEmpCode : TextView? = view.txtEmpCode
    var txtEmail : TextView? = view.txtEmail
}