package com.example.todolist_ramkumartextiles

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.todolist_ramkumartextiles.activity.MainActivity
import com.example.todolist_ramkumartextiles.models.TaskInformation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_assign_task.*
import kotlinx.android.synthetic.main.fragment_assign_task.view.*
import java.util.*
import kotlin.collections.ArrayList


class AssignTaskFrag : Fragment() {


    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_assign_task, container, false)
        /*
        *   items = new ArrayList<>();
        Item c1= new Item("Rahul","9902374623",R.drawable.man_without_specs);
        Item c2 = new Item("Riya","9783432725",R.drawable.girl);
        Item c3 = new Item("Aditya","7436984332",R.drawable.boy);
        ListView listView=(ListView)v.findViewById(R.id.list_view);
        items.add(c1);
        items.add(c2);
        items.add(c3);
        ListAdapter adapter =  new Listadapter(items, getActivity());
        listView.setAdapter(adapter);*/


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
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        if (view.employee_name != null)
        {
            val arrayAdapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item,usersList) }
            view.employee_name.adapter = arrayAdapter

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
                    dateString = "$day/$month/$year"
                    tV_dateA.text = dateString

                },now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
            }

            if (datePickerDialog != null) {
                datePickerDialog.show()
            }
        }

        view.submitTask.setOnClickListener {
            val taskDesc = desc.text.toString()
            val status = false
            if(TextUtils.isEmpty(employeeName) || TextUtils.isEmpty(taskDesc))
            {
                Toast.makeText(context,"Incomplete Task", Toast.LENGTH_SHORT).show()
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
                Toast.makeText(context,"Uploaded Task", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }



}
