package com.example.todolist_ramkumartextiles.tasks.data.room.dataclasses

import androidx.room.Entity

@Entity(tableName = "taskInfo")
data class TaskInformation(
    var employeeName:String="",
    var desc: String = "",
    var date:String = "",
    var status:Boolean=false,
    var taskId:String?=""
){
    constructor():this("","","",false,"")
}
