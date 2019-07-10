package com.example.todolist_ramkumartextiles;

public class TaskInformation
{
    public String employeeName, desc, date, taskId;
    public Boolean status;

    public TaskInformation() {
    }

    public TaskInformation(String employeeName, String desc, String date,Boolean status, String taskId) {
        this.employeeName = employeeName;
        this.desc = desc;
        this.date = date;
        this.status = status;
        this.taskId = taskId;
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

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
