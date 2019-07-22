package com.example.todolist_ramkumartextiles.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todolist_ramkumartextiles.R;
import com.example.todolist_ramkumartextiles.models.TaskInformation;

import java.util.ArrayList;

public class RecycleAdapt extends RecyclerView.Adapter<RecycleAdapt.ViewHolder>
{
    private ArrayList<TaskInformation> items;

    public RecycleAdapt(ArrayList<TaskInformation> items, Context context)
    {
         this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.task_layout,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        viewHolder.task.setText(items.get(i).getDesc());
        viewHolder.date.setText(items.get(i).getDate());
        viewHolder.status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    items.get(i).setStatus(b);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView task, date;
        CheckBox status;

        public ViewHolder(View itemView) {
            super(itemView);
            task = itemView.findViewById(R.id.tV_task);
            date = itemView.findViewById(R.id.tV_date);
            status = itemView.findViewById(R.id.status);
        }
    }
}