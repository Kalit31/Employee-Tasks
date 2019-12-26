package com.example.todolist_ramkumartextiles.owners.views.adapters


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.example.todolist_ramkumartextiles.R
import com.example.todolist_ramkumartextiles.owners.views.activity.InfoAct

import java.util.ArrayList

class RecycleAdapter_Info(private val employees: ArrayList<String>, private val context: Context?) :
    RecyclerView.Adapter<RecycleAdapter_Info.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(R.layout.employee_info_layout, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.employee.text = employees[i]
        viewHolder.layout.setOnClickListener {
            val intent = Intent(context, InfoAct::class.java)
            intent.putExtra("username", employees[i])
            context?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return employees.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var employee: TextView
        internal var layout: LinearLayout

        init {
            employee = itemView.findViewById(R.id.tV_employee_info)
            layout = itemView.findViewById(R.id.employee_info_layout)
        }
    }
}