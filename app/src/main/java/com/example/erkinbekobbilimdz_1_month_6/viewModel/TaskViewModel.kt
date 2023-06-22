package com.example.erkinbekobbilimdz_1_month_6.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.erkinbekobbilimdz_1_month_6.model.Task

class TaskViewModel: ViewModel() {
    val taskList: MutableLiveData<List<Task>?> = MutableLiveData()

    init {
        taskList.value = listOf()
    }

    fun addTask(title : String){
        val currentList = taskList.value?.toMutableList()?: mutableListOf()
        currentList.add(Task(title))
        taskList.value = currentList

    }

    fun removeTask(position: Int){
        val currentList = taskList.value?.toMutableList()
        if (position >= 0 && position < (currentList?.size ?: 0)) {
            currentList?.removeAt(position)
            taskList.value = currentList
        }
    }
}