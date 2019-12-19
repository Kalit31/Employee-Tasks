package com.example.todolist_ramkumartextiles.fragment


import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.todolist_ramkumartextiles.R
import com.example.todolist_ramkumartextiles.adapters.RecycleAdapt_Completed
import com.example.todolist_ramkumartextiles.model.TaskInformation
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_completed.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CompletedFrag : Fragment() {

    private lateinit var databaseReference: DatabaseReference
    private var items = ArrayList<TaskInformation>()
    private lateinit var adapter : RecycleAdapt_Completed
    private lateinit var username:String
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_completed, container, false)

        sharedPreferences = context!!.getSharedPreferences("LoginPref", Context.MODE_PRIVATE)
        username = sharedPreferences.getString("username"," ")!!
        databaseReference = FirebaseDatabase.getInstance().getReference("Completed").
            child(username)
        val date = Date()

        databaseReference.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SimpleDateFormat")
            @TargetApi(Build.VERSION_CODES.O)
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                items.clear()
                for (ds in dataSnapshot.children)
                {
                    val item: TaskInformation? = ds.getValue(TaskInformation::class.java)
                    if (item != null) {
                        var dateComplete = item.date
                        val d1 = SimpleDateFormat("dd-MM-yyyy").parse(dateComplete)
                        val calendar = Calendar.getInstance()
                        calendar.time = d1
                        calendar.add(Calendar.DAY_OF_WEEK,+7)
                        val d2 = calendar.time
                        if(date <= d2)
                           items.add(item)
                        else{
                            val newRef = FirebaseDatabase.getInstance().getReference("Completed").
                                    child(username).child(item.taskId.toString())
                            newRef.removeValue()
                        }
                    }
                }
                adapter = RecycleAdapt_Completed(items, context)
                v.recyclerView_completed.adapter = adapter
                v.recyclerView_completed.layoutManager = LinearLayoutManager(context)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,error.toString(), Toast.LENGTH_SHORT).show()
            }
        })
        return v
    }
}