package com.example.iwanttobelieve.data.repository

import com.example.iwanttobelieve.data.model.User
import com.example.iwanttobelieve.utils.Constants
import com.example.iwanttobelieve.utils.Result
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

// Repositório para lidar com dados de usuários no Firestore
class UserRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private val usersCollection = db.collection(Constants.USERS_COLLECTION)

    // Cria ou atualiza perfil de usuário
    suspend fun createUser(user: User): Result<Void?> {
        return try {
            usersCollection.document(user.uid).set(user).await()
            Result.Success(null)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    // Busca perfil de um usuário pelo UID
    suspend fun getUser(uid: String): Result<User?> {
        return try {
            val snapshot = usersCollection.document(uid).get().await()
            val user = snapshot.toObject(User::class.java)
            Result.Success(user)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
