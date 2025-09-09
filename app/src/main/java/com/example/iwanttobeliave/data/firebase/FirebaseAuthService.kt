package com.example.iwanttobelieve.data.firebase

import com.google.firebase.auth.FirebaseAuth

class FirebaseAuthService(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {
    fun getCurrentUser() = auth.currentUser
    fun signOut() = auth.signOut()
}