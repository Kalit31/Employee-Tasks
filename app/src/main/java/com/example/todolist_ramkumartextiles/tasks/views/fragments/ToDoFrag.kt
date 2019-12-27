package com.example.todolist_ramkumartextiles.tasks.views.fragments


import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.todolist_ramkumartextiles.R
import com.example.todolist_ramkumartextiles.di.AppModule
import com.example.todolist_ramkumartextiles.tasks.views.adapters.RecycleAdapt
import com.example.todolist_ramkumartextiles.tasks.data.room.dataclasses.TaskInformation
import com.example.todolist_ramkumartextiles.tasks.viewmodel.TaskToDoViewModel
import com.example.todolist_ramkumartextiles.tasks.viewmodel.TaskToDoViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_to_do.*
import kotlinx.android.synthetic.main.fragment_to_do.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ToDoFrag : Fragment() {

    private lateinit var adapter : RecycleAdapt
    private lateinit var taskViewModel:TaskToDoViewModel

    @SuppressLint("FragmentLiveDataObserve")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_to_do, container, false)

        taskViewModel = ViewModelProviders.of(this,TaskToDoViewModelFactory()).get(TaskToDoViewModel::class.java)

        adapter = RecycleAdapt()
        v.recyclerView.layoutManager = LinearLayoutManager(context)
        v.recyclerView.adapter = adapter

        taskViewModel.getTasks().observe(viewLifecycleOwner,androidx.lifecycle.Observer {
            adapter.submitList(it)
        })

        v.updateTask.setOnClickListener {
            taskViewModel.updateTasks()
        }
        return v
    }
}