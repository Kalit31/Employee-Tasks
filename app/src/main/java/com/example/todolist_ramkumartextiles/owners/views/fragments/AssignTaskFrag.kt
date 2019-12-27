package com.example.todolist_ramkumartextiles.owners.views.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.todolist_ramkumartextiles.R
import com.example.todolist_ramkumartextiles.owners.viewmodel.AssignTaskViewModel
import com.example.todolist_ramkumartextiles.owners.viewmodel.AssignTaskViewModelFactory
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_assign_task.*
import kotlinx.android.synthetic.main.fragment_assign_task.view.*
import java.util.*
import kotlin.collections.ArrayList


class AssignTaskFrag : Fragment() {

    private lateinit var assignTaskViewModel: AssignTaskViewModel
    private var employeeName:String = ""
    private val CHANNEL_ID = "notification_id"
    private val CHANNEL_NAME = "notification_name"
    private val CHANNEL_DESC = "notification_desc"

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view =  inflater.inflate(R.layout.fragment_assign_task, container, false)

        assignTaskViewModel = ViewModelProviders.of(this,AssignTaskViewModelFactory()).get(AssignTaskViewModel::class.java)

        var usersList: ArrayList<String>
        var dateString = ""

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            var channel = NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = CHANNEL_DESC
            val notificationManager = context!!.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        assignTaskViewModel.removeNotifications()

        assignTaskViewModel.getUsers().observe(viewLifecycleOwner,androidx.lifecycle.Observer {
            usersList = it
            if (view.employee_name != null)
            {
                val arrayAdapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item,usersList) }
                view.employee_name.adapter = (arrayAdapter as SpinnerAdapter?)!!

                view.employee_name.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(p0: AdapterView<*>?) {}
                    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                        employeeName = usersList[position]
                    }
                }
            }
        })

        view.submitTask.setOnClickListener {
            assignTaskViewModel.submitTask(employeeName,desc.text.toString(),dateString).observe(viewLifecycleOwner,androidx.lifecycle.Observer {
                Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                if(it.complete){
                    desc.setText("")
                    dateString = ""
                    tV_dateA.text = "Date"
                }
            })
        }

        view.date.setOnClickListener {
            val now = Calendar.getInstance()
            context?.let { it1 ->
                DatePickerDialog(it1, DatePickerDialog.OnDateSetListener { _, year, month, day ->
                    val m = month + 1
                    dateString = "$day/$m/$year"
                    tV_dateA.text = dateString
                },now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
            }?.show()
        }

        return view
    }
}
