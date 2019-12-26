package com.example.todolist_ramkumartextiles.owners.views.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.Toast
import com.example.todolist_ramkumartextiles.R
import com.example.todolist_ramkumartextiles.tasks.data.room.dataclasses.TaskInformation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_assign_task.*
import kotlinx.android.synthetic.main.fragment_assign_task.view.*
import java.util.*
import kotlin.collections.ArrayList


class AssignTaskFrag : Fragment() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private var employeeName:String = ""
    private final val CHANNEL_ID = "notification_id"
    private final val CHANNEL_NAME = "notification_name"
    private final val CHANNEL_DESC = "notification_desc"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view =  inflater.inflate(R.layout.fragment_assign_task, container, false)

        var usersList = ArrayList<String>()

        auth = FirebaseAuth.getInstance()

        var dateString: String = ""
        databaseReference = FirebaseDatabase.getInstance().getReference("Usernames")

         val removeRef = FirebaseDatabase.getInstance().getReference("notificationsRequests")
         removeRef.removeValue()

        usersList.add("Select Employee")

        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // whenever data at this location is updated.
                for (ds in dataSnapshot.children) {
                    val user = ds.getValue(String::class.java)
                    usersList.add(user!!)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            var channel = NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = CHANNEL_DESC
            val notificationManager = context!!.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }


        if (view.employee_name != null)
        {
            val arrayAdapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item,usersList) }
            view.employee_name.adapter = (arrayAdapter as SpinnerAdapter?)!!

            view.employee_name.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    employeeName = usersList[position]
                }
            }


        }

        view.date.setOnClickListener {
            val now = Calendar.getInstance()
            val datePickerDialog = context?.let { it1 ->
                DatePickerDialog(it1, DatePickerDialog.OnDateSetListener { _, year, month, day ->
                    val m = month + 1
                    dateString = "$day/$m/$year"
                    tV_dateA.text = dateString

                },now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
            }

            if (datePickerDialog != null) {
                datePickerDialog.show()
            }
        }

        view.submitTask.setOnClickListener {
            var taskDesc = desc.text.toString()
            val status = false
            if(TextUtils.isEmpty(employeeName) || TextUtils.isEmpty(taskDesc)|| TextUtils.isEmpty(dateString))
            {
                Toast.makeText(context,"Incomplete Task", Toast.LENGTH_SHORT).show()
            }
            else if(employeeName == "Select Employee"){
                Toast.makeText(context,"Please select an employee",Toast.LENGTH_SHORT).show()
            }
            else
            {
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

                val notifications = FirebaseDatabase.getInstance().getReference("notificationsRequests").child(employeeName)
                notifications.push().setValue(taskDesc)

                desc.setText("")
                dateString = ""
                taskDesc = ""
                tV_dateA.text = "Date"
                Toast.makeText(context,"Uploaded Task", Toast.LENGTH_SHORT).show()


            }
        }
        return view
    }
}