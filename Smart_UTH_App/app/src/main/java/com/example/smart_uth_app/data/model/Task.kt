package com.example.smart_uth_app.data.model

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val status: String,
    val priority: String,
    val due: String
)

data class TasksResponse(
    val tasks: List<Task>
)
