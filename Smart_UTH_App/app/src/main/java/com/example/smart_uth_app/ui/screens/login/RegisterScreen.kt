package com.example.smart_uth_app.ui.screens.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smart_uth_app.viewmodel.AuthEvent
import com.example.smart_uth_app.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(onSuccess: () -> Unit, onBack: () -> Unit, vm: AuthViewModel = viewModel()) {
    val ctx = LocalContext.current
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        vm.events.collect { ev ->
            when (ev) {
                is AuthEvent.Ok -> Toast.makeText(ctx, ev.msg, Toast.LENGTH_SHORT).show()
                is AuthEvent.Error -> Toast.makeText(ctx, ev.msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Create Account", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, singleLine = true)
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = pass, onValueChange = { pass = it }, label = { Text("Password") },
            singleLine = true, visualTransformation = PasswordVisualTransformation())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = confirm, onValueChange = { confirm = it }, label = { Text("Confirm password") },
            singleLine = true, visualTransformation = PasswordVisualTransformation())
        Spacer(Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (pass != confirm) {
                    Toast.makeText(ctx, "Passwords do not match", Toast.LENGTH_SHORT).show()
                } else vm.signUp(email, pass, onSuccess)
            }
        ) { Text("Sign up") }
        TextButton(onClick = onBack) { Text("Back to login") }
    }
}