package com.example.todolist_ramkumartextiles.tasks.data.room.dataclasses

data class TaskInformation(
    var employeeName:String="",
    var desc: String = "",
    var date:String = "",
    var status:Boolean=false,
    var taskId:String?=""
){
    constructor():this("","","",false,"")
}
