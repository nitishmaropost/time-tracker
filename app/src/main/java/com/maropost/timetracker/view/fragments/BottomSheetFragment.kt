package com.maropost.timetracker.view.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.core.content.ContextCompat
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.CalendarView
import android.widget.TextView
import com.maropost.timetracker.R
import com.maropost.commons.utils.Utility
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
    private var startDate = ""
    private var endDate = ""

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
        setupCalendar()
    }

    /**
     * Get startDate and endDate calculated from AttendanceDetail fragment
     */
    fun setDate(startDate: String, endDate: String){
        this.startDate = startDate
        this.endDate = endDate
    }

    /**
     * Set values in calendar view
     */
    private fun setupCalendar() {
        dateType = DATETYPE.START
        // Check if startDate and endDate values are assigned coming from attendance detail fragment
        if(!TextUtils.isEmpty(startDate) && !TextUtils.isEmpty(endDate)) {
            txtStartDate.text = startDate
            txtEndDate.text = endDate
            setCalanderDateTextColors()
            setCalendarToSpecificDate(startDate)
        }
    }

    /**
     * Setup onClick listeners
     */
    private fun initialiseListeners(dialog: Dialog?) {
        dialog?.calendarView?.setOnDateChangeListener { view, year, month, day ->
            setCalenderDetails(year, month, day)
        }

        dialog?.btnClear?.setOnClickListener{
            setCurrentWeekDateValues()
        }
        dialog?.btnSet?.setOnClickListener {
            bottomSheetCallbacks.onDateSelected(txtStartDate.text.toString(),txtEndDate.text.toString())
        }

        dialog?.lnrStartDate?.setOnClickListener{
            dateType = DATETYPE.START
            setCalanderDateTextColors()
            setCalendarToSpecificDate(txtStartDate.text.toString())
        }

        dialog?.lnrEndDate?.setOnClickListener{
            dateType = DATETYPE.END
            setCalanderDateTextColors()
            setCalendarToSpecificDate(txtEndDate.text.toString())
        }
    }

    /**
     * When user clears the filter, set the calendar to first and last day of week
     */
    private fun setCurrentWeekDateValues() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK,calendar.firstDayOfWeek)
        startDate = Utility.getInstance().getCurrentDate(calendar)
        calendar.add(Calendar.DAY_OF_WEEK, 6)
        endDate = Utility.getInstance().getCurrentDate(calendar)
        setupCalendar()
    }

    /**
     * Change text colors for start date and end date
     */
    private fun setCalanderDateTextColors() {
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
        selectedDate = Utility.getInstance().getCurrentDate(calendar)
        setCalendarToSpecificDate(selectedDate)
        when(dateType){
            DATETYPE.START -> txtStartDate.text = selectedDate
            DATETYPE.END -> txtEndDate.text = selectedDate
        }
    }

    /**
     * Show selected date value in calender view
     */
    private fun setCalendarToSpecificDate(strDate: String){
        calendarView.setDate ( SimpleDateFormat("dd-MM-yyyy").parse(strDate).time, true, true)
    }

    /**
     * Initialise callback
     */
    fun setCallback(bottomSheetCallbacks: BottomSheetCallbacks) {
        this.bottomSheetCallbacks = bottomSheetCallbacks
    }

    interface BottomSheetCallbacks{
        fun onDateSelected(startDate: String, endDate: String)
    }
}