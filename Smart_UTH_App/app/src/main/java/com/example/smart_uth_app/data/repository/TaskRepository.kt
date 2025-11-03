package com.example.smart_uth_app.data.repository

import com.example.smart_uth_app.data.model.Task
import com.example.smart_uth_app.data.network.ApiClient

class TaskRepository {
    private val api = ApiClient.instance

    suspend fun getAll(): List<Task> = api.getTasks().tasks
    suspend fun getDetail(id: Int): Task = api.getTaskDetail(id)
    suspend fun delete(id: Int) { api.deleteTask(id) }
}