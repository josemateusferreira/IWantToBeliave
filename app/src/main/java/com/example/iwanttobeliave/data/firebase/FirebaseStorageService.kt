package com.example.iwanttobelieve.data.firebase

import com.google.firebase.storage.FirebaseStorage

// Serviço para lidar com upload/download de arquivos no Storage
class FirebaseStorageService(
    val storage: FirebaseStorage = FirebaseStorage.getInstance()
)
