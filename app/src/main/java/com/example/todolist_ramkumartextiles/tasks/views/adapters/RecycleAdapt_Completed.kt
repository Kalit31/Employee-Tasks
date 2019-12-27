package com.example.todolist_ramkumartextiles.tasks.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist_ramkumartextiles.R
import com.example.todolist_ramkumartextiles.tasks.data.room.dataclasses.TaskInformation

import java.util.ArrayList

class RecycleAdapt_Completed() : RecyclerView.Adapter<RecycleAdapt_Completed.ViewHolder>() {

    private var items : ArrayList<TaskInformation> = ArrayList()

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(R.layout.employee_completed, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.task.text = items[i].desc
        viewHolder.date.text = items[i].date
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(items:ArrayList<TaskInformation>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var task: TextView
        internal var date: TextView


        init {
            task = itemView.findViewById(R.id.tV_task_completed)
            date = itemView.findViewById(R.id.tV_date_completed)

        }
    }
}