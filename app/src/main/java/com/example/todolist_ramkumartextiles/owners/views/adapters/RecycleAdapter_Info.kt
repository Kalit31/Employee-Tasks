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

class RecycleAdapter_Info( private val context: Context?) :
    RecyclerView.Adapter<RecycleAdapter_Info.ViewHolder>() {

    private var items : ArrayList<String> = ArrayList()

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(R.layout.employee_info_layout, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.employee.text = items[i]
        viewHolder.layout.setOnClickListener {
            val intent = Intent(context, InfoAct::class.java)
            intent.putExtra("username", items[i])
            context?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(items:ArrayList<String>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var employee: TextView = itemView.findViewById(R.id.tV_employee_info)
        internal var layout: LinearLayout = itemView.findViewById(R.id.employee_info_layout)

    }
}