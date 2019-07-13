package com.example.todolist_ramkumartextiles.models;

public class EmployeeStatus
{
    String username;
    int count;

    public EmployeeStatus() {
    }

    public EmployeeStatus(String username, int count) {
        this.username = username;
        this.count = count;

    }



    public String getEmployee() {
        return username;
    }

    public void setEmployee(String username) {
        this.username = username;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
