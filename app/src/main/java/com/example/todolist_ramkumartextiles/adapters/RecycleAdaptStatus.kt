package com.example.todolist_ramkumartextiles.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.example.todolist_ramkumartextiles.R
import com.example.todolist_ramkumartextiles.activity.TaskRemaining

import java.util.ArrayList

class RecycleAdaptStatus(private val items: ArrayList<String>, private val context: Context?) :
    RecyclerView.Adapter<RecycleAdaptStatus.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(R.layout.employee_info_layout, viewGroup, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.employee.text = items[i]
        viewHolder.employeePop.setOnClickListener {
            val intent = Intent(context, TaskRemaining::class.java)
            intent.putExtra("username", items[i])
            context?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var employee: TextView
        internal var employeePop: LinearLayout

        init {
            employee = itemView.findViewById(R.id.tV_employee_info)
            employeePop = itemView.findViewById(R.id.employee_info_layout)
        }
    }
}
