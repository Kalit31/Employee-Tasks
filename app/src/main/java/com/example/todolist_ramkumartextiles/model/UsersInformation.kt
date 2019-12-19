package com.example.todolist_ramkumartextiles.model

data class UsersInformation(
    var username: String? = "",
    var email: String? = "",
    var password: String? = "",
    var token: String? = ""
){
    constructor():this("","","","")
}
