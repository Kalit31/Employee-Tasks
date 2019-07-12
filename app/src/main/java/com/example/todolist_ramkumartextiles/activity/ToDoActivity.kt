package com.example.todolist_ramkumartextiles.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist_ramkumartextiles.R
import com.example.todolist_ramkumartextiles.adapters.RecycleAdapt
import com.example.todolist_ramkumartextiles.models.TaskInformation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_to_do.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ToDoActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private var items = ArrayList<TaskInformation>()
    private lateinit var adapter : RecycleAdapt

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do)

        auth = FirebaseAuth.getInstance()

        val username = intent.getStringExtra("username").toString()
        //val username = "Kalit"
        if(auth.currentUser == null)
        {
            finish()
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(username).child("tasks")

        //val user = auth.currentUser

        textView.text = "Welcome  $username"

        logout.setOnClickListener {
            auth.signOut()
            finish()
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }

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
                Toast.makeText(applicationContext,"Reached here", Toast.LENGTH_SHORT).show()
                adapter = RecycleAdapt(items, applicationContext)
                recyclerView.layoutManager = LinearLayoutManager(this@ToDoActivity)
                recyclerView.adapter= adapter

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,error.toString(),Toast.LENGTH_SHORT).show()
            }
        })

        updateTask.setOnClickListener {
            for(item in this.items)
            {
                if(item.getStatus())
                {
                    val ref = databaseReference.child(item.getTaskId())
                    ref.removeValue()
                }

            }
        }
    }
}
