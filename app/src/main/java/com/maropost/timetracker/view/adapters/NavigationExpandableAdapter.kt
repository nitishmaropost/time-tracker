package com.maropost.timetracker.view.adapters

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import com.maropost.timetracker.R
import com.maropost.timetracker.pojomodels.NavigationMenuGroup
import com.maropost.timetracker.pojomodels.NavigationMenuChild
import android.widget.TextView
import kotlinx.android.synthetic.main.list_group_child.view.*
import kotlinx.android.synthetic.main.list_group_header.view.*


class NavigationExpandableAdapter(private val context: Context, private var listDataHeader: ArrayList<NavigationMenuGroup>,
                                  private var listDataChild: HashMap<String, ArrayList<NavigationMenuChild>>) : BaseExpandableListAdapter() {

    override fun getChild(groupPosition: Int, childPosititon: Int): NavigationMenuChild? {
        return listDataChild[listDataHeader[groupPosition].groupName]!![childPosititon]
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getChildView(groupPosition: Int, childPosition: Int,
                              isLastChild: Boolean, convertView: View?, parent: ViewGroup): View? {
        var mConvertView = convertView

        val childText = getChild(groupPosition, childPosition)
        if (mConvertView == null) {
            val inflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            mConvertView = inflater.inflate(R.layout.list_group_child, null)
        }
        val tvLCTitle = mConvertView?.tvLCTitle as TextView
        tvLCTitle.text= childText!!.childName

        val ivLCIcon = mConvertView?.ivLCIcon as ImageView
        ivLCIcon.setImageResource(childText!!.childIcon)

        return mConvertView
    }
    override fun getChildrenCount(groupPosition: Int): Int {

        return if (this.listDataChild[this.listDataHeader[groupPosition].groupName] == null) 0
        else this.listDataChild[this.listDataHeader[groupPosition].groupName]!!.size
    }

    override fun getGroup(groupPosition: Int): NavigationMenuGroup {
        return this.listDataHeader[groupPosition]
    }

    override fun getGroupCount(): Int {
        return this.listDataHeader.size
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean,
                              convertView: View?, parent: ViewGroup): View? {
        var mConvertView = convertView
        val groupHeader = getGroup(groupPosition)
        if (mConvertView == null) {
            val inflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            mConvertView = inflater.inflate(R.layout.list_group_header,null)
        }
        val imgGroupHeader = mConvertView?.imgGroupHeader as ImageView
        imgGroupHeader.setImageResource(groupHeader.groupIcon)

        val lblListHeader = mConvertView?.lblListHeader as TextView
        lblListHeader.setTypeface(null, Typeface.BOLD)
        lblListHeader.text= groupHeader.groupName

        if (listDataChild[listDataHeader[groupPosition].groupName]!= null){
            mConvertView?.imgArrow.visibility= View.VISIBLE
            if(groupHeader.groupArrowStatus == NavigationMenuGroup.GroupStatus.EXPANDED)
                mConvertView?.imgArrow?.setImageResource(R.drawable.ic_expand_less_black_24dp)
            else
                mConvertView?.imgArrow?.setImageResource(R.drawable.ic_expand_more_black_24dp)
        }
        return mConvertView
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}