package com.example.todolist_ramkumartextiles.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todolist_ramkumartextiles.R;
import com.example.todolist_ramkumartextiles.activity.InfoAct;
import com.example.todolist_ramkumartextiles.models.TaskInformation;
import com.google.firebase.database.*;

import java.util.ArrayList;

public class RecycleAdapter_Info extends RecyclerView.Adapter<RecycleAdapter_Info.ViewHolder>
{

    private ArrayList<String> employees;
    private Context context;

    public RecycleAdapter_Info(ArrayList<String> items, Context context)
    {
        this.employees = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.employee_info_layout,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        viewHolder.employee.setText(employees.get(i));
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InfoAct.class);
                intent.putExtra("username",employees.get(i));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView employee;
        LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            employee = itemView.findViewById(R.id.tV_employee_info);
            layout = itemView.findViewById(R.id.employee_info_layout);
        }
    }
}