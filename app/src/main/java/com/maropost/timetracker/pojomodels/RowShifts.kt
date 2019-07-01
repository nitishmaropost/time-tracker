package com.maropost.timetracker.pojomodels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RowShifts {
     val absents: Int = 0
     val holidays: Int = 0
     val on_premises_time: Long = 0
     val shifts = ArrayList<Shifts>()
     val user_data: UserData? = null
     val work_time: Long = 0
     val presents: Int = 0
}