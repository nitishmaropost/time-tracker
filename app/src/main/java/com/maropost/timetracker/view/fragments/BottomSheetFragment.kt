package com.maropost.timetracker.view.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.widget.CalendarView
import android.widget.TextView
import com.maropost.timetracker.R
import kotlinx.android.synthetic.main.fragment_bottom_sheet_dialog.*
import java.text.SimpleDateFormat
import java.util.*

class BottomSheetFragment() : BottomSheetDialogFragment() {

    private var selectedDate = ""
    private lateinit var dateType : DATETYPE
    private lateinit var txtStartDate : TextView
    private lateinit var txtEndDate : TextView
    private lateinit var calendarView : CalendarView
    private lateinit var bottomSheetCallbacks: BottomSheetCallbacks

    enum class DATETYPE{
        START,
        END
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog?, style: Int) {
        super.setupDialog(dialog, style)
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_bottom_sheet_dialog, null);
        dialog?.setContentView(view)
        txtStartDate = view.findViewById(R.id.txtStartDate)
        txtEndDate = view.findViewById(R.id.txtEndDate)
        calendarView = view.findViewById(R.id.calendarView)
        initialiseListeners(dialog)
        getCurrentWeekDateValues()

    }

    /**
     * Set first and last date of current week
     */
    private fun getCurrentWeekDateValues() {
        dateType = DATETYPE.START
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK,calendar.firstDayOfWeek)
        val firstDayOfWeek = getFormattedDate(calendar)
        txtStartDate.text = firstDayOfWeek
        calendar.add(Calendar.DAY_OF_WEEK, 6)
        val lastDayOfWeek = getFormattedDate(calendar)
        txtEndDate.text = lastDayOfWeek
        setupInfo()
        setCalendarToSpecificDate(firstDayOfWeek)
    }

    /**
     * Setup onClick listeners
     */
    private fun initialiseListeners(dialog: Dialog?) {
        dialog?.calendarView?.setOnDateChangeListener { view, year, month, day ->
            setCalenderDetails(year, month, day)
        }

        dialog?.btnClear?.setOnClickListener{
            getCurrentWeekDateValues()
        }
        dialog?.btnSet?.setOnClickListener {
            bottomSheetCallbacks.onDateSelected(txtStartDate.text.toString(),txtEndDate.text.toString())
        }

        dialog?.lnrStartDate?.setOnClickListener{
            dateType = DATETYPE.START
            setupInfo()
            setCalendarToSpecificDate(txtStartDate.text.toString())
        }

        dialog?.lnrEndDate?.setOnClickListener{
            dateType = DATETYPE.END
            setupInfo()
            setCalendarToSpecificDate(txtEndDate.text.toString())
        }
    }

    private fun setupInfo() {
        when(dateType){
            DATETYPE.START -> {
                txtStartDate.setTextColor(ContextCompat.getColor(activity!!, R.color.colorBlack))
                txtEndDate.setTextColor(ContextCompat.getColor(activity!!, R.color.light_gray))
            }
            DATETYPE.END -> {
                txtStartDate.setTextColor(ContextCompat.getColor(activity!!, R.color.light_gray))
                txtEndDate.setTextColor(ContextCompat.getColor(activity!!, R.color.colorBlack))
            }
        }

    }

    /**
     * Set selected date from picker
     */
    private fun setCalenderDetails(year: Int, month: Int, day: Int){
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        selectedDate = getFormattedDate(calendar)
        setCalendarToSpecificDate(selectedDate)
        when(dateType){
            DATETYPE.START -> txtStartDate.text = selectedDate
            DATETYPE.END -> txtEndDate.text = selectedDate
        }
    }

    private fun getFormattedDate(calendar: Calendar):String{
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        return sdf.format(calendar.time)
    }

    /**
     * Show selected date value in calender view
     */
    private fun setCalendarToSpecificDate(strDate: String){
        calendarView.setDate ( SimpleDateFormat("dd-MM-yyyy").parse(strDate).time, true, true)
    }

    fun setCallback(bottomSheetCallbacks: BottomSheetCallbacks) {
        this.bottomSheetCallbacks = bottomSheetCallbacks
    }

    interface BottomSheetCallbacks{
        fun onDateSelected(startDate: String, endDate: String)
    }
}