package com.codinginflow.ToDoUpdatedVer.UI.tasks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.codinginflow.ToDoUpdatedVer.classes.TaskDao

class TasksVM @ViewModelInject constructor(
    private val taskDao: TaskDao
) : ViewModel(){

    val tasks = taskDao.getTasks().asLiveData() //hold tasks list as live data
}