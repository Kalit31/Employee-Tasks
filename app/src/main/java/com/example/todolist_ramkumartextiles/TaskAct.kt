package com.example.todolist_ramkumartextiles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_task.*

class TaskAct : AppCompatActivity() {


    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        auth = FirebaseAuth.getInstance()

        databaseReference = FirebaseDatabase.getInstance().getReference()

       // var user: FirebaseUser? = auth.currentUser

        submitTask.setOnClickListener {
            val employeeName = employee_name.text.toString()
            val taskDesc = desc.text.toString()
            val date = date.text.toString()

            if(TextUtils.isEmpty(employeeName)||TextUtils.isEmpty(taskDesc)||TextUtils.isEmpty(date))
            {
                Toast.makeText(applicationContext,"Incomplete Task", Toast.LENGTH_SHORT).show()
            }
            else
            {
                val taskInfo: TaskInformation = TaskInformation(employeeName,taskDesc,date)
                databaseReference.push().setValue(taskInfo)
                Toast.makeText(applicationContext,"Uploaded Task", Toast.LENGTH_SHORT).show()
            }

        }


    }

}
