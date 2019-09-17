package com.maropost.management.time.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.maropost.management.R
import com.maropost.management.time.pojomodels.TimeUtils
import kotlinx.android.synthetic.main.time_details_card_item.view.*
import java.util.ArrayList

class TimeDetailsAdapter(private var timeDetailList : ArrayList<TimeUtils>, val context: Context) : androidx.recyclerview.widget.RecyclerView.Adapter<TimeDetailsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeDetailsViewHolder {
        return TimeDetailsViewHolder(LayoutInflater.from(context).inflate(R.layout.time_details_card_item, parent, false))
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: TimeDetailsViewHolder, position: Int) {
        holder.tvInTime?.text= "10:05 AM"
        holder.tvOutTime?.text= "07:10 PM"
    }
}

class TimeDetailsViewHolder(view: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
    var tvInTime: TextView? = view.tvInTime
    var tvOutTime: TextView? = view.tvOutTime
}
