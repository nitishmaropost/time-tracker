package com.maropost.timetracker.view.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maropost.timetracker.R
import com.maropost.timetracker.pojomodels.NavigationItem
import kotlinx.android.synthetic.main.list_item.view.*

class NavigationAdapter(private val arrayList : ArrayList<NavigationItem>, private val context: Context,
                   private val callback: NavigationAdapterCallbacks) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.itemView.txtItem.text = arrayList[position].itemName
        /*
        holder.itemView.imgFruit.setOnClickListener{
            fruitAdapterCallbacks.onItemClick(holder.itemView.imgFruit,arrayList[position])
        }*/
    }
}

interface NavigationAdapterCallbacks{
    //fun onItemClick(image: ImageView,fruitType: FruitType)
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view)
