package com.example.iwanttobelieve.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iwanttobelieve.utils.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : ViewModel() {

    private val _authState = MutableStateFlow<Result<Any>?>(null)
    val authState: StateFlow<Result<Any>?> = _authState

    // Registrar usuário
    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            _authState.value = Result.Loading
            try {
                val authResult = auth.createUserWithEmailAndPassword(email, password).await()

                // Atualiza displayName
                auth.currentUser?.updateProfile(userProfileChangeRequest {
                    displayName = name
                })?.await()

                // Cria documento no Firestore
                authResult.user?.uid?.let { uid ->
                    val userData = mapOf(
                        "name" to name,
                        "email" to email
                    )
                    db.collection("users").document(uid).set(userData).await()
                }

                _authState.value = Result.Success(Unit)
            } catch (e: Exception) {
                _authState.value = Result.Error(e)
            }
        }
    }

    // Login
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = Result.Loading
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                _authState.value = Result.Success(Unit)
            } catch (e: Exception) {
                _authState.value = Result.Error(e)
            }
        }
    }

    // Logout
    fun logout() {
        auth.signOut()
    }

    // Retorna usuário logado
    fun currentUser() = auth.currentUser

    fun setError(message: String) {
        _authState.value = Result.Error(Exception(message))
    }
}
