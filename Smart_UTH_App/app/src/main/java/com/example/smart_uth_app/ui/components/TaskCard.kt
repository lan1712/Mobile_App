package com.example.smart_uth_app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.smart_uth_app.data.model.Task

@Composable
fun TaskCard(task: Task, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(task.title, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(6.dp))
            Text(task.description, maxLines = 2)
            Spacer(Modifier.height(6.dp))
            Text("Status: ${task.status} • Priority: ${task.priority} • Due: ${task.due}")
        }
    }
}