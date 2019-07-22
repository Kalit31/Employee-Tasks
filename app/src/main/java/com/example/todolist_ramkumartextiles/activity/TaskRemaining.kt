package com.example.todolist_ramkumartextiles.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist_ramkumartextiles.R
import com.example.todolist_ramkumartextiles.adapters.RecycleAdapt
import com.example.todolist_ramkumartextiles.adapters.RecycleAdapt_Completed
import com.example.todolist_ramkumartextiles.models.TaskInformation
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_task_remaining.*

class TaskRemaining : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference
    private var items = ArrayList<TaskInformation>()
    private lateinit var adapter: RecycleAdapt_Completed

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_remaining)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width = dm.widthPixels
        val height = dm.heightPixels

        window.setLayout((width * .8).toInt(), (height * .6).toInt())

        val username = intent.getStringExtra("username")

        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(username).
                child("tasks")

        databaseReference.addValueEventListener(object :ValueEventListener
        {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for(ds in p0.children)
                {
                    val item: TaskInformation? = ds.getValue(TaskInformation::class.java)
                    if (item != null) {
                        items.add(item)
                    }
                    adapter = RecycleAdapt_Completed(items,applicationContext)
                    task_remaining_rv.layoutManager = LinearLayoutManager(this@TaskRemaining)
                    task_remaining_rv.adapter = adapter
                }

            }

        })

    }
}
