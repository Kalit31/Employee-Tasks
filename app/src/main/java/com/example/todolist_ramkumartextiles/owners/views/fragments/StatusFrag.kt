package com.example.todolist_ramkumartextiles.owners.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist_ramkumartextiles.R
import com.example.todolist_ramkumartextiles.owners.views.adapters.RecycleAdaptStatus
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_status.view.*
import kotlin.collections.ArrayList


class StatusFrag : Fragment() {

    private lateinit var databaseReference: DatabaseReference
    private var counter = ArrayList<String>()
    private var employeeList = ArrayList<String>()
    private lateinit var adapter: RecycleAdaptStatus
    private var uploadedUser = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view =  inflater.inflate(R.layout.fragment_status, container, false)
        val ref = FirebaseDatabase.getInstance().getReference("Usernames")
        ref.addValueEventListener(object:ValueEventListener
        {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (ds in p0.children) {
                    val user = ds.getValue(String::class.java)
                    if(!(user in counter)) {
                        employeeList.add(user!!)
                        counter.add(user)
                    }
                }
                adapter =
                    RecycleAdaptStatus(
                        employeeList,
                        context
                    )
                view.recyclerView_status.layoutManager = LinearLayoutManager(context) as RecyclerView.LayoutManager?
                view.recyclerView_status.adapter= adapter
            }
        })
        return view
    }

    private fun readUsers(firebaseCallback: FirebaseCallback) {
        var ref = FirebaseDatabase.getInstance().getReference("Usernames")
        ref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                employeeList.clear()
                for (ds in dataSnapshot.children) {
                    val user = ds.getValue(String::class.java)
                    if (user != null) {
                        employeeList.add(user)
                        val ref2 = FirebaseDatabase.getInstance().getReference("Users")
                            .child(user).child("count")
                        ref2.addValueEventListener(object :ValueEventListener{
                            override fun onCancelled(p0: DatabaseError) {
                                }

                            override fun onDataChange(p0: DataSnapshot) {

                            }

                        })
                    }
                }
                firebaseCallback.onCallback(employeeList)
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
            }
        })

    }

    private interface FirebaseCallback{
        fun onCallback(list:ArrayList<String>)
    }
}

/*databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                employeeList.clear()
                for (ds in dataSnapshot.children) {
                    val user:EmployeeStatus? = ds.getValue(EmployeeStatus::class.java)
                    if(user!!.employee !in uploadedUser) {
                        employeeStatusList.add(user)
                        uploadedUser.add(user.employee)
                    }
                }
                adapter = RecycleAdaptStatus(employeeStatusList,context)
                view.recyclerView_status.layoutManager = LinearLayoutManager(context) as RecyclerView.LayoutManager?
                view.recyclerView_status.adapter= adapter
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
            }
        })*/