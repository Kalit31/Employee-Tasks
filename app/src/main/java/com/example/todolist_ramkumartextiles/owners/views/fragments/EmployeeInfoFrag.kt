package com.example.todolist_ramkumartextiles.owners.views.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.todolist_ramkumartextiles.R
import com.example.todolist_ramkumartextiles.owners.viewmodel.EmployeeInfoViewModel
import com.example.todolist_ramkumartextiles.owners.viewmodel.EmployeeInfoViewModelFactory
import com.example.todolist_ramkumartextiles.owners.views.adapters.RecycleAdapter_Info
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_employee_info.view.*


class EmployeeInfoFrag : Fragment() {

    private lateinit var adapter : RecycleAdapter_Info
    private lateinit var employeeViewModel:EmployeeInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_employee_info, container, false)

        employeeViewModel = ViewModelProviders.of(this,EmployeeInfoViewModelFactory()).get(EmployeeInfoViewModel::class.java)

        adapter = RecycleAdapter_Info(context)
        v.employee_info_rv.adapter = adapter
        v.employee_info_rv.layoutManager = LinearLayoutManager(context)


        employeeViewModel.getUsers().observe(viewLifecycleOwner, Observer {
                it.removeAt(0)
                adapter.submitList(it)
        })
        return v
    }
}
