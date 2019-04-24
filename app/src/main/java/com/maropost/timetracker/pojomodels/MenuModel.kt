package com.maropost.timetracker.pojomodels

class MenuModel{

    var groupName: String = ""
    var groupIcon: Int = 0
    var groupArrowStatus: GroupStatus = GroupStatus.COLLAPSED


    enum class GroupStatus {
        EXPANDED,
        COLLAPSED
    }
}