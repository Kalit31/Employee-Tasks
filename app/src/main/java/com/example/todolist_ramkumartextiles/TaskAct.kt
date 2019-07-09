package com.example.todolist_ramkumartextiles

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.DatePicker
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_task.*
import java.util.*

class TaskAct : AppCompatActivity(){


    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        auth = FirebaseAuth.getInstance()



        var dateString: String = ""

      date.setOnClickListener {
          val now = Calendar.getInstance()
          val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener {
                  _, year, month, day ->
              dateString = "$day/$month/$year"
              tV_dateA.text = dateString

          },now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))

          datePickerDialog.show()
      }

        submitTask.setOnClickListener {
            val employeeName: String = employee_name.text.toString()
            val taskDesc = desc.text.toString()
            val status = false
            if(TextUtils.isEmpty(employeeName) || TextUtils.isEmpty(taskDesc))
            {
                Toast.makeText(applicationContext,"Incomplete Task", Toast.LENGTH_SHORT).show()
            }
            else
            {
                val taskInfo: TaskInformation = TaskInformation(employeeName, taskDesc, dateString,status)
                databaseReference = FirebaseDatabase.getInstance().getReference(employeeName.split('@','.')[0])
                databaseReference.push().setValue(taskInfo)
                Toast.makeText(applicationContext,"Uploaded Task", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
