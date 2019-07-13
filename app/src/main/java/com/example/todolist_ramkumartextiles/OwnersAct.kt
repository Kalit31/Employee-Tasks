package com.example.todolist_ramkumartextiles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_owners.*

class OwnersAct : AppCompatActivity() {

    private  val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {item ->

        var selectedFragment: Fragment? = null

        when(item.itemId)
        {
            R.id.task ->{
                selectedFragment = AssignTaskFrag()
                }
            R.id.status ->{
                selectedFragment = StatusFrag()
              }
            R.id.location->{
                selectedFragment = MapsFrag()
              }
        }

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment!!).commit()
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owners)

         bottom_nav.setOnNavigationItemSelectedListener (mOnNavigationItemSelectedListener)
        val startFrag = AssignTaskFrag()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, startFrag).commit()
    }

   }


