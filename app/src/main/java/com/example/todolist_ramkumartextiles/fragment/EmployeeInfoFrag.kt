package com.example.todolist_ramkumartextiles.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.todolist_ramkumartextiles.R
import com.example.todolist_ramkumartextiles.adapters.RecycleAdapter_Info
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_employee_info.view.*


class EmployeeInfoFrag : Fragment() {

    private var employees = ArrayList<String>()
    private var counter = ArrayList<String>()
    private lateinit var adapter : RecycleAdapter_Info

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_employee_info, container, false)

        val ref = FirebaseDatabase.getInstance().getReference("Usernames")
        ref.addValueEventListener(object:ValueEventListener
        {
            override fun onCancelled(p0: DatabaseError) {
                }

            override fun onDataChange(p0: DataSnapshot) {
                for (ds in p0.children) {
                    val user = ds.getValue(String::class.java)
                    if(!(user in counter)) {
                        employees.add(user!!)
                        counter.add(user)
                    }
                }
                adapter = RecycleAdapter_Info(employees, context)
                v.employee_info_rv.adapter = adapter
                v.employee_info_rv.layoutManager = LinearLayoutManager(context)
            }
        })
        return v
    }
}
