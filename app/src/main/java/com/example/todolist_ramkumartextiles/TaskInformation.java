package com.example.todolist_ramkumartextiles;

public class TaskInformation
{
    public String employeeName, desc, date;
    public Boolean status;

    public TaskInformation() {
    }

    public TaskInformation(String employeeName, String desc, String date,Boolean status) {
        this.employeeName = employeeName;
        this.desc = desc;
        this.date = date;
        this.status = status;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
