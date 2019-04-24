package com.maropost.timetracker.view.adapters

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.maropost.timetracker.pojomodels.MenuChildModel
import com.maropost.timetracker.pojomodels.MenuModel
import com.maropost.timetracker.R
import kotlinx.android.synthetic.main.list_group_child.view.*
import kotlinx.android.synthetic.main.list_group_header.view.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ExpandableListAdapter(private val context: Context, private var listDataHeader: ArrayList<MenuModel>,
                            private var listDataChild: HashMap<String, ArrayList<MenuChildModel>>) : BaseExpandableListAdapter() {

    override fun getChild(groupPosition: Int, childPosititon: Int): MenuChildModel? {
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
        mConvertView?.lblListItem?.text = childText!!.childName
        mConvertView?.imgChildHeader?.setImageResource(childText.childIcon)
        return mConvertView
    }

    override fun getChildrenCount(groupPosition: Int): Int {

        return if (this.listDataChild[this.listDataHeader[groupPosition].groupName] == null) 0
        else this.listDataChild[this.listDataHeader[groupPosition].groupName]!!.size
    }

    override fun getGroup(groupPosition: Int): MenuModel {
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
            mConvertView = inflater.inflate(R.layout.list_group_header, null)
        }
        mConvertView?.imgGroupHeader?.setImageResource(groupHeader.groupIcon)
        mConvertView?.lblListHeader?.setTypeface(null, Typeface.BOLD)
        mConvertView?.lblListHeader?.text = groupHeader.groupName
        return mConvertView
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}