package com.example.todolist_ramkumartextiles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist_ramkumartextiles.adapters.RecycleAdaptStatus
import com.example.todolist_ramkumartextiles.models.EmployeeStatus
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_status.view.*
import kotlin.collections.ArrayList

class StatusFrag : Fragment() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private var employeeList = ArrayList<String>()
    private var employeeStatusList = ArrayList<EmployeeStatus>()
    private lateinit var adapter: RecycleAdaptStatus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view =  inflater.inflate(R.layout.fragment_status, container, false)
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                employeeList.clear()
                for (ds in dataSnapshot.children) {
                    val user:EmployeeStatus? = ds.getValue(EmployeeStatus::class.java)
                   employeeStatusList.add(user!!)
                }
                adapter = RecycleAdaptStatus(employeeStatusList)
                view.recyclerView_status.layoutManager = LinearLayoutManager(context)
                view.recyclerView_status.adapter= adapter
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
            }
        })
        return view
    }
}
