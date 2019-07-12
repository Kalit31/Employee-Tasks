package com.example.todolist_ramkumartextiles.activity

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.todolist_ramkumartextiles.R
import com.example.todolist_ramkumartextiles.models.TaskInformation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
//import kotlinx.android.synthetic.main.activity_task.*
import java.util.*
import kotlin.collections.ArrayList


class TaskAct() : AppCompatActivity(){


    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth


  /*  override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

            var usersList = ArrayList<String>()

            auth = FirebaseAuth.getInstance()
            var employeeName:String = ""
            var dateString: String = ""
            databaseReference = FirebaseDatabase.getInstance().getReference("Usernames")

            usersList.add("Select Employee")

        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (ds in dataSnapshot.children) {
                    val user = ds.getValue(String::class.java)
                    usersList.add(user!!)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        if (employee_name != null)
        {
         val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,usersList)
         employee_name.adapter = arrayAdapter

         employee_name.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
             override fun onNothingSelected(p0: AdapterView<*>?) {

             }

             override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                 employeeName = usersList[position]
                  }

         }

        }


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
            val taskDesc = desc.text.toString()
            val status = false
            if(TextUtils.isEmpty(employeeName) || TextUtils.isEmpty(taskDesc))
            {
                Toast.makeText(applicationContext,"Incomplete Task", Toast.LENGTH_SHORT).show()
            }
            else
            {
                //employeeName.split('@','.')[0]
                databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                val taskId = databaseReference.push().key
                val taskInfo: TaskInformation =
                    TaskInformation(
                        employeeName,
                        taskDesc,
                        dateString,
                        status,
                        taskId
                    )
                databaseReference.child(employeeName).child("tasks").child(taskId!!).setValue(taskInfo)
                Toast.makeText(applicationContext,"Uploaded Task", Toast.LENGTH_SHORT).show()
            }
        }

        logout_owners.setOnClickListener {
            finish()
            var sharedPreferences = getSharedPreferences("LoginPref", Context.MODE_PRIVATE)
            val logoutEdit: SharedPreferences.Editor = sharedPreferences.edit()
            logoutEdit.putBoolean("login",false)
            logoutEdit.apply()
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
    }*/
}
