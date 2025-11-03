package com.example.smart_uth_app.ui.screens.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smart_uth_app.data.model.Task
import com.example.smart_uth_app.data.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun DetailScreen(id: Int, onBack: () -> Unit) {
    var detail by remember { mutableStateOf<Task?>(null) }

    LaunchedEffect(id) {
        detail = runCatching {
            withContext(Dispatchers.IO) { TaskRepository().getDetail(id) }
        }.getOrNull()
    }

    Column(Modifier.padding(16.dp)) {
        if (detail == null) {
            Text("Loading…")
        } else {
            Text("Title: ${detail!!.title}")
            Spacer(Modifier.height(8.dp))
            Text(detail!!.description)
            Spacer(Modifier.height(16.dp))
            Button(onClick = {
//                // gọi API xóa → quay lại
//                LaunchedEffect(Unit) {
//                    withContext(Dispatchers.IO) { TaskRepository().delete(id) }
//                    onBack()
//                }
            }) { Text("Delete Task") }
        }
    }
}