package com.example.todolist_ramkumartextiles.tasks.data.repo

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.util.Log
import com.example.todolist_ramkumartextiles.tasks.data.room.dataclasses.TaskInformation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class TaskRepository(val sharedPreferences: SharedPreferences){
    companion object {
        val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    }


    var username:String = sharedPreferences.getString("PREF_KEY_CURRENT_USER_NAME","User").toString()

    fun loadTasks(taskCallback: TaskCallback) {
        var databaseReference = database.getReference("Users").child(username!!).child("tasks")
        var tasks:ArrayList<TaskInformation> = ArrayList<TaskInformation>()
        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                tasks.clear()
                for (ds in dataSnapshot.children)
                {
                    val item: TaskInformation? = ds.getValue(
                        TaskInformation::class.java)
                    if (item != null) {
                           tasks.add(item)
                    }
                }
                 taskCallback.onCallback(tasks)
            }
        })
    }

    fun loadCompletedTasks(taskCallback: TaskCallback){
        val databaseReference =  database.getReference("Completed").child(username)
        val tasks:ArrayList<TaskInformation> = ArrayList<TaskInformation>()
        val date = Date()

        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}

            @SuppressLint("SimpleDateFormat")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children)
                {
                    val item: TaskInformation? = ds.getValue(
                        TaskInformation::class.java)
                    if (item != null) {
                        var dateComplete = item.date
                        val d1 = SimpleDateFormat("dd-MM-yyyy").parse(dateComplete)
                        val calendar = Calendar.getInstance()
                        calendar.time = d1
                        calendar.add(Calendar.DAY_OF_WEEK,+7)
                        val d2 = calendar.time
                        if(date <= d2)
                            tasks.add(item)
                        else{
                            val newRef = FirebaseDatabase.getInstance().getReference("Completed").
                                child(username).child(item.taskId.toString())
                            newRef.removeValue()
                        }
                    }
                }
                Log.d("tasks",tasks.size.toString())
                taskCallback.onCallback(tasks)
            }
        })
    }


    fun removeTask(task: TaskInformation){
        val ref = database.getReference("Users").child(username).child("tasks").child(task.taskId.toString())
        val newRef = FirebaseDatabase.getInstance().getReference("Completed")
        val date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        task.date = date
        newRef.child(username).child(task.taskId.toString()).setValue(task)
        ref.removeValue()
    }

    interface TaskCallback{
        fun onCallback(tasks:ArrayList<TaskInformation>)
    }
}