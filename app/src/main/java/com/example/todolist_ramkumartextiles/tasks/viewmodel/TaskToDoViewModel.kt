package com.example.todolist_ramkumartextiles.tasks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist_ramkumartextiles.tasks.data.repo.TaskRepository
import com.example.todolist_ramkumartextiles.tasks.data.room.dataclasses.TaskInformation

class TaskToDoViewModel(
    private val taskRepository: TaskRepository
): ViewModel() {

    var taskData : MutableLiveData<ArrayList<TaskInformation>> = MutableLiveData()

    init{
        fetchTasks()
    }

    fun getTasks(): LiveData<ArrayList<TaskInformation>>{
        return taskData
    }

    fun fetchTasks(){
        taskRepository.loadTasks(object : TaskRepository.TaskCallback{
            override fun onCallback(tasks: ArrayList<TaskInformation>) {
                taskData.value= tasks
            }
        })
    }

    fun updateTasks(){
        for(task in taskData.value!!)
        {
            if(task.status){
                taskRepository.removeTask(task)
            }
        }
    }
}