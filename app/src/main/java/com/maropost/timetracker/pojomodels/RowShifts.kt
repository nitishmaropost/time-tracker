package com.maropost.timetracker.pojomodels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RowShifts {
     val absents = ""
     val holidays = ""
     val on_premises_time = ""
     val shifts = ArrayList<Shifts>()
     val user_data: UserData? = null
     val work_time = ""
     val presents = ""
}