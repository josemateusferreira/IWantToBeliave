package com.example.iwanttobelieve.data.firebase

import com.google.firebase.firestore.FirebaseFirestore

// Serviço para lidar com banco de dados Firestore
class FirebaseFirestoreService(
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
)
