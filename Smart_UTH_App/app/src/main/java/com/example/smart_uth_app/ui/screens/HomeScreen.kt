package com.example.smart_uth_app.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smart_uth_app.ui.components.TaskCard
import com.example.smart_uth_app.viewmodel.TaskEvent
import com.example.smart_uth_app.viewmodel.TaskViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(onOpenDetail: (Int) -> Unit, viewModel: TaskViewModel = viewModel()) {
    val tasks by viewModel.tasks.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is TaskEvent.Error -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { padding ->
        if (tasks.isEmpty()) {
            EmptyView()
        } else {
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(tasks) { t -> TaskCard(task = t) { onOpenDetail(t.id) } }
            }
        }
    }
}
