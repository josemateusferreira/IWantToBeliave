package com.example.iwanttobelieve.data.repository

import com.example.iwanttobelieve.utils.Result
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

// Repositório para autenticação de usuários
class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {
    // Cadastro com email e senha
    suspend fun register(email: String, password: String): Result<String> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            Result.Success(result.user?.uid ?: "")
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    // Login com email e senha
    suspend fun login(email: String, password: String): Result<String> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Result.Success(result.user?.uid ?: "")
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    // Logout
    fun logout() {
        auth.signOut()
    }

    // Usuário logado atualmente
    fun currentUser() = auth.currentUser
}
