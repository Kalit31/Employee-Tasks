package com.example.todolist_ramkumartextiles.tasks.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist_ramkumartextiles.R
import com.example.todolist_ramkumartextiles.tasks.data.room.dataclasses.TaskInformation
import kotlinx.android.synthetic.main.task_layout.view.*

class RecycleAdapt : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var items : ArrayList<TaskInformation> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return RecycleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.task_layout, parent, false)
        )

    }

    override fun getItemCount(): Int {
            return  items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when(holder){
                is RecycleViewHolder ->{
                    holder.bind(items.get(position))
                }
            }
    }

    fun submitList(items:ArrayList<TaskInformation>) {
        this.items = items
        notifyDataSetChanged()
    }

    class RecycleViewHolder constructor(
        itemView:View
    ):RecyclerView.ViewHolder(itemView){
        val task = itemView.tV_task
        val date = itemView.tV_date
        val status = itemView.status

        fun bind(taskInfo : TaskInformation){
            task.setText(taskInfo.desc)
            date.setText(taskInfo.date)
            status.setOnCheckedChangeListener{compoundButton, b ->
                taskInfo.status = b
            }
        }

    }



}