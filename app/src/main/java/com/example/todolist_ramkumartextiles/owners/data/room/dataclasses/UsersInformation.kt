package com.example.todolist_ramkumartextiles.owners.data.room.dataclasses

data class UsersInformation(
    var username: String? = "",
    var email: String? = "",
    var password: String? = "",
    var token: String? = ""
){
    constructor():this("","","","")
}
