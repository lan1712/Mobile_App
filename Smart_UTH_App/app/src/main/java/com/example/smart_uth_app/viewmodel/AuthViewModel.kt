package com.example.smart_uth_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

sealed class AuthEvent { data class Ok(val msg:String):AuthEvent(); data class Error(val msg:String):AuthEvent() }

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val _events = Channel<AuthEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun signIn(email: String, pass: String, onSuccess: () -> Unit) = viewModelScope.launch {
        if (email.isBlank() || pass.isBlank()) return@launch _events.send(AuthEvent.Error("Email & password required"))
        auth.signInWithEmailAndPassword(email, pass)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception -> viewModelScope.launch { _events.send(AuthEvent.Error(exception.message ?: "Sign in failed")) } }
    }

    fun signUp(email: String, pass: String, onSuccess: () -> Unit) = viewModelScope.launch {
        if (pass.length < 6) return@launch _events.send(AuthEvent.Error("Password ≥ 6 chars"))
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception -> viewModelScope.launch { _events.send(AuthEvent.Error(exception.message ?: "Sign up failed")) } }
    }

    fun reset(email: String) = viewModelScope.launch {
        if (email.isBlank()) return@launch _events.send(AuthEvent.Error("Enter email"))
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener { viewModelScope.launch { _events.send(AuthEvent.Ok("Reset email sent")) } }
            .addOnFailureListener { exception -> viewModelScope.launch { _events.send(AuthEvent.Error(exception.message ?: "Failed")) } }
    }
}