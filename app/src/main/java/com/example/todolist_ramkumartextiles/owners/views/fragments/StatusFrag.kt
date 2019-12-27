package com.example.todolist_ramkumartextiles.owners.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist_ramkumartextiles.R
import com.example.todolist_ramkumartextiles.owners.viewmodel.StatusViewModel
import com.example.todolist_ramkumartextiles.owners.viewmodel.StatusViewModelFactory
import com.example.todolist_ramkumartextiles.owners.views.adapters.RecycleAdaptStatus
import kotlinx.android.synthetic.main.fragment_status.view.*


class StatusFrag : Fragment() {

    private lateinit var adapter: RecycleAdaptStatus
    private lateinit var statusViewModel:StatusViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view =  inflater.inflate(R.layout.fragment_status, container, false)

        statusViewModel = ViewModelProviders.of(this,StatusViewModelFactory()).get(StatusViewModel::class.java)

        adapter =RecycleAdaptStatus(context)
        view.recyclerView_status.layoutManager = LinearLayoutManager(context) as RecyclerView.LayoutManager?
        view.recyclerView_status.adapter= adapter

        statusViewModel.getUsers().observe(viewLifecycleOwner, Observer {
            it.removeAt(0)
            adapter.submitList(it)
        })

        return view
    }
}