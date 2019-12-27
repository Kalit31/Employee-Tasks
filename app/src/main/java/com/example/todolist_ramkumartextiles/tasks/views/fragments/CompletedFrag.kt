package com.example.todolist_ramkumartextiles.tasks.views.fragments


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
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.todolist_ramkumartextiles.R
import com.example.todolist_ramkumartextiles.tasks.views.adapters.RecycleAdapt_Completed
import com.example.todolist_ramkumartextiles.tasks.data.room.dataclasses.TaskInformation
import com.example.todolist_ramkumartextiles.tasks.viewmodel.TaskCompViewModelFactory
import com.example.todolist_ramkumartextiles.tasks.viewmodel.TaskCompletedViewModel
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_completed.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CompletedFrag : Fragment() {

    private lateinit var taskViewModel:TaskCompletedViewModel
    private lateinit var adapter : RecycleAdapt_Completed


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_completed, container, false)

        taskViewModel = ViewModelProviders.of(this,TaskCompViewModelFactory()).get(TaskCompletedViewModel::class.java)

        adapter = RecycleAdapt_Completed()
        v.recyclerView_completed.adapter = adapter
        v.recyclerView_completed.layoutManager = LinearLayoutManager(context)

        taskViewModel.getTasks().observe(viewLifecycleOwner,androidx.lifecycle.Observer {
            adapter.submitList(it)
        })
        return v
    }
}