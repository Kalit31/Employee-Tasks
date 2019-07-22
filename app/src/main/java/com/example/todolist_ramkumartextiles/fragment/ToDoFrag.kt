package com.example.todolist_ramkumartextiles.fragment


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.todolist_ramkumartextiles.R
import com.example.todolist_ramkumartextiles.activity.LoginActivity
import com.example.todolist_ramkumartextiles.adapters.RecycleAdapt
import com.example.todolist_ramkumartextiles.models.TaskInformation
import com.example.todolist_ramkumartextiles.services.LocationService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_to_do.*
import kotlinx.android.synthetic.main.fragment_to_do.*
import kotlinx.android.synthetic.main.fragment_to_do.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ToDoFrag : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private var items = ArrayList<TaskInformation>()
    private lateinit var adapter : RecycleAdapt
    private lateinit var username:String
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_to_do, container, false)


        sharedPreferences = context!!.getSharedPreferences("LoginPref", Context.MODE_PRIVATE)
        username = sharedPreferences.getString("username"," ")!!
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").
            child(username).child("tasks")

        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                items.clear()
                for (ds in dataSnapshot.children)
                {
                    val item: TaskInformation? = ds.getValue(TaskInformation::class.java)
                    if (item != null) {
                        items.add(item)
                    }
                }
                var c = items.size
                var myRef = FirebaseDatabase.getInstance().getReference("Users").child(username)
                myRef.child("count").setValue(c)
                adapter = RecycleAdapt(items, context)
                v.recyclerView.layoutManager = LinearLayoutManager(context)
                v.recyclerView.adapter = adapter
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,error.toString(), Toast.LENGTH_SHORT).show()
            }
        })
        v.updateTask.setOnClickListener {
            for(item in this.items)
            {
                if(item.status)
                {
                    val ref = databaseReference.child(item.taskId)
                    val newRef = FirebaseDatabase.getInstance().getReference("Completed")
                    val date = SimpleDateFormat("dd-MM-yyyy",Locale.getDefault()).format(Date())
                    item.date = date
                    newRef.child(username).child(item.taskId).setValue(item)
                    ref.removeValue()
                }
            }
            val d =Date().toString()
        }
        return v
    }
}
