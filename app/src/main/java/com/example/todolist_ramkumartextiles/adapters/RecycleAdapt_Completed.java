package com.example.todolist_ramkumartextiles.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todolist_ramkumartextiles.R;
import com.example.todolist_ramkumartextiles.models.TaskInformation;

import java.util.ArrayList;

public class RecycleAdapt_Completed extends RecyclerView.Adapter<RecycleAdapt_Completed.ViewHolder>
{

    private ArrayList<TaskInformation> items;


    public RecycleAdapt_Completed(ArrayList<TaskInformation> items, Context context)
    {
         this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.employee_completed,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        viewHolder.task.setText(items.get(i).getDesc());
        viewHolder.date.setText(items.get(i).getDate());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView task, date;


        public ViewHolder(View itemView) {
            super(itemView);
            task = itemView.findViewById(R.id.tV_task_completed);
            date = itemView.findViewById(R.id.tV_date_completed);

        }
    }
}