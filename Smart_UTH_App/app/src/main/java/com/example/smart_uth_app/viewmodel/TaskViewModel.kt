package com.example.smart_uth_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smart_uth_app.data.model.Task
import com.example.smart_uth_app.data.repository.TaskRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

sealed class TaskEvent {
    data class Error(val message: String) : TaskEvent()
}

class TaskViewModel : ViewModel() {

    private val repository = TaskRepository()

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks = _tasks.asStateFlow()

    private val _event = Channel<TaskEvent>()
    val event = _event.receiveAsFlow()

    init {
        fetchTasks()
    }

    private fun fetchTasks() {
        viewModelScope.launch {
            try {
                _tasks.value = repository.getAll()
            } catch (e: Exception) {
                _event.send(TaskEvent.Error(e.message ?: "An unknown error occurred"))
            }
        }
    }
}
