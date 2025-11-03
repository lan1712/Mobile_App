package com.example.smart_uth_app.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smart_uth_app.viewmodel.AuthViewModel

@Composable
fun ForgotPasswordScreen(onBack: () -> Unit, vm: AuthViewModel = viewModel()) {
    var email by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Reset Password", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, singleLine = true)
        Spacer(Modifier.height(12.dp))
        Button(modifier = Modifier.fillMaxWidth(), onClick = { vm.reset(email) }) { Text("Send reset email") }
        TextButton(onClick = onBack) { Text("Back to login") }
    }
}