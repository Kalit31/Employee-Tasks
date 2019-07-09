package com.example.todolist_ramkumartextiles

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_to_do.*

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


        if(auth.currentUser == null)
        {
            finish()
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }
        val user = auth.currentUser
        val u = user!!.email.toString()
        databaseReference = FirebaseDatabase.getInstance().getReference(u.split('@','.')[0])


        textView.text = "Welcome  " + user.email!!.substringBefore('@')

        logout.setOnClickListener {
            auth.signOut()
            finish()
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }
        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for (ds in dataSnapshot.children)
                {
                    val item: TaskInformation? = ds.getValue(TaskInformation::class.java)
                    if (item != null) {
                        items.add(item)
                    }

                }
                Toast.makeText(applicationContext,"Reached here", Toast.LENGTH_SHORT).show()
                adapter = RecycleAdapt(items)
                recyclerView.layoutManager = LinearLayoutManager(this@ToDoActivity)
                //recyclerView.hasFixedSize() = true
                recyclerView.adapter= adapter

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            //    Log.w(TAG, "Failed to read value.", error.toException())
                Toast.makeText(applicationContext,error.toString(),Toast.LENGTH_SHORT).show()
            }
        })

    }
}

