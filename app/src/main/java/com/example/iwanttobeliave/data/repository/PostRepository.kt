package com.example.iwanttobelieve.data.repository

import com.example.iwanttobelieve.data.model.Post
import com.example.iwanttobelieve.utils.Constants
import com.example.iwanttobelieve.utils.Result
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.channels.awaitClose

// Repositório para lidar com publicações no Firestore
class PostRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private val postsCollection = db.collection(Constants.POSTS_COLLECTION)

    // Cria uma nova publicação
    suspend fun createPost(post: Post): Result<Void?> {
        return try {
            val doc = postsCollection.document()
            val postWithId = post.copy(id = doc.id)
            doc.set(postWithId).await()
            Result.Success(null)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    // Observa as publicações em tempo real
    fun getPosts(): Flow<Result<List<Post>>> = callbackFlow {
        val listener = postsCollection
            .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    trySend(Result.Error(e))
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val posts = snapshot.toObjects(Post::class.java)
                    trySend(Result.Success(posts))
                }
            }
        awaitClose { listener.remove() }
    }
}
