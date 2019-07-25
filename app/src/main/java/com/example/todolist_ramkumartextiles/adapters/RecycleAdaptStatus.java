package com.example.todolist_ramkumartextiles.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todolist_ramkumartextiles.R;
import com.example.todolist_ramkumartextiles.activity.TaskRemaining;
import com.example.todolist_ramkumartextiles.models.EmployeeStatus;

import java.util.ArrayList;

public class RecycleAdaptStatus extends RecyclerView.Adapter<RecycleAdaptStatus.ViewHolder>
{

    private ArrayList<String> items;
    private Context context;

    public RecycleAdaptStatus(ArrayList<String> items,Context context)
    {

        this.items = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.employee_info_layout,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i)
    {
        viewHolder.employee.setText(items.get(i));
        viewHolder.employeePop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TaskRemaining.class);
                intent.putExtra("username",items.get(i));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView employee;
        LinearLayout employeePop;

        public ViewHolder(View itemView) {
            super(itemView);
            employee = itemView.findViewById(R.id.tV_employee_info);
            employeePop = itemView.findViewById(R.id.employee_info_layout);
        }
    }
}
