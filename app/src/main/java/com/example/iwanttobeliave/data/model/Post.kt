package com.example.iwanttobelieve.data.model

import com.google.firebase.Timestamp

data class Post(
    val id: String = "",
    val authorUid: String = "",
    val authorName: String = "",
    val imageUrl: String = "", // URL da imagem
    val description: String = "",
    val timestamp: Timestamp = Timestamp.now()
)
