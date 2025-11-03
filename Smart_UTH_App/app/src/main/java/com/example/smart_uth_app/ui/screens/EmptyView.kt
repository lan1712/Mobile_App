package com.example.smart_uth_app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp




@Composable
fun EmptyView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.DateRange, contentDescription = null, modifier = Modifier.size(64.dp))
        Spacer(Modifier.height(8.dp))
        Text("No Tasks Yet!", style = MaterialTheme.typography.titleMedium)
        Text("Stay productive—add something to do",
            color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}