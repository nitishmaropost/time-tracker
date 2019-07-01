package com.maropost.timetracker.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.maropost.timetracker.R
import com.maropost.timetracker.pojomodels.Shifts
import com.maropost.timetracker.utils.Utility
import kotlinx.android.synthetic.main.item_shifts_fragment.view.*
import java.util.*

class ShiftsAdapter(private var arrayList: ArrayList<Shifts>, val context: Context,
                    private val shiftsAdapterCallbacks: ShiftsAdapterCallbacks) : RecyclerView.Adapter<ShiftsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShiftsViewHolder {
        return ShiftsViewHolder(LayoutInflater.from(context).inflate(R.layout.item_shifts_fragment, parent, false))
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ShiftsViewHolder, position: Int) {
        holder.txtDate?.text = Utility.getInstance().getDateFromString(arrayList[position].dated_str)
        when(arrayList[position].status){
            "holiday" -> {
                holder.txtWorkTime?.text = "Holiday"
                holder.txtPremisisTime?.visibility = View.GONE
                holder.txtWorkTime?.setTextColor(ContextCompat.getColor(context, R.color.blue))

            }
                "absent" -> {
                    holder.txtWorkTime?.text = "Absent"
                    holder.txtPremisisTime?.visibility = View.GONE
                    holder.txtWorkTime?.setTextColor(ContextCompat.getColor(context, R.color.colorRed))
                }
            "present" ->{
                holder.txtWorkTime?.setTextColor(ContextCompat.getColor(context, R.color.colorBlack))
                holder.txtWorkTime?.text = context.getString(R.string.work_time) + " " + arrayList[position].work_time.div(3600) + " " + context.getString(R.string.hrs)
                holder.txtPremisisTime?.text = context.getString(R.string.premisis_time) + " " + arrayList[position].on_premises_time.div(3600) + " " + context.getString(R.string.hrs)
                holder.txtPremisisTime?.visibility = View.VISIBLE
            }
        }
    }
}

interface ShiftsAdapterCallbacks{
    fun onItemClick(shifts: Shifts)
}

class ShiftsViewHolder(view: View): RecyclerView.ViewHolder(view) {
    var txtDate: TextView? = view.txtDate
    var txtDay: TextView? = view.txtDay
    var txtWorkTime : TextView? = view.txtWorkTime
    var txtPremisisTime : TextView? = view.txtPremisisTime
    var lnrItem : LinearLayout? = view.lnrItem
}