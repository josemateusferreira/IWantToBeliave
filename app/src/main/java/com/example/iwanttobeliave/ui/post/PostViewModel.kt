package com.example.iwanttobelieve.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iwanttobelieve.data.model.Post
import com.example.iwanttobelieve.data.repository.PostRepository
import com.example.iwanttobelieve.utils.Result
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostViewModel(
    private val postRepository: PostRepository = PostRepository(),
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : ViewModel() {

    private val _postState = MutableStateFlow<Result<Void?>?>(null)
    val postState: StateFlow<Result<Void?>?> = _postState

    fun createPost(description: String, imageUrl: String) {
        viewModelScope.launch {
            _postState.value = Result.Loading
            try {
                val currentUser = auth.currentUser
                val uid = currentUser?.uid ?: ""
                var name = "Usuário"

                if (uid.isNotEmpty()) {
                    val userDoc = db.collection("users").document(uid).get().await()
                    name = userDoc.getString("name") ?: "Usuário"
                }

                val post = Post(
                    authorUid = uid,
                    authorName = name,
                    imageUrl = imageUrl,
                    description = description,
                    timestamp = Timestamp.now()
                )

                val result = postRepository.createPost(post)
                _postState.value = result
            } catch (e: Exception) {
                _postState.value = Result.Error(e)
            }
        }
    }
}
