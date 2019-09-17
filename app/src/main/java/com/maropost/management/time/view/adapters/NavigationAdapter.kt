package com.maropost.management.time.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maropost.management.R
import com.maropost.management.time.pojomodels.NavigationItem
import kotlinx.android.synthetic.main.list_item.view.*

class NavigationAdapter(
    private val arrayList: ArrayList<NavigationItem>, private val context: Context,
    private val callback: NavigationAdapterCallbacks
) : androidx.recyclerview.widget.RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.txtItem.text = arrayList[position].itemName
        holder.itemView.imgItem.setImageResource(arrayList[position].itemImage)

        holder.itemView.setOnClickListener { callback.onItemClick(arrayList[position].itemName) }
    }
}

interface NavigationAdapterCallbacks {
    fun onItemClick(menuItem: String)
}

class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)
