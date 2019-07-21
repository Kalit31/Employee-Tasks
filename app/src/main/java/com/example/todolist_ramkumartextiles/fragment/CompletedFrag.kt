package com.example.todolist_ramkumartextiles.fragment


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.todolist_ramkumartextiles.R
import com.example.todolist_ramkumartextiles.adapters.RecycleAdapt
import com.example.todolist_ramkumartextiles.adapters.RecycleAdapt_Completed
import com.example.todolist_ramkumartextiles.models.TaskInformation
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_completed.view.*


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
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_completed, container, false)

        sharedPreferences = context!!.getSharedPreferences("LoginPref", Context.MODE_PRIVATE)
        username = sharedPreferences.getString("username"," ")!!
        databaseReference = FirebaseDatabase.getInstance().getReference("Completed").
            child(username)

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
