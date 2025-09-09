package com.example.iwanttobelieve.data.model

// Representa um usuário da rede social
data class User(
    val uid: String = "",          // UID do Firebase Auth
    val name: String = "",         // Nome do usuário
    val email: String = "",        // Email do usuário
    val profileImageUrl: String? = null // URL da foto de perfil (pode ser null)
)
