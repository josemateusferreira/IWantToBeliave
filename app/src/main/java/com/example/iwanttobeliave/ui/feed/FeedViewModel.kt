package com.example.iwanttobelieve.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iwanttobelieve.data.model.Post
import com.example.iwanttobelieve.data.repository.PostRepository
import com.example.iwanttobelieve.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// ViewModel que controla o feed de postagens
class FeedViewModel(
    private val postRepository: PostRepository = PostRepository()
) : ViewModel() {

    private val _posts = MutableStateFlow<Result<List<Post>>>(Result.Loading)
    val posts: StateFlow<Result<List<Post>>> = _posts

    init {
        loadPosts()
    }

    // Observa os posts em tempo real
    private fun loadPosts() {
        viewModelScope.launch {
            postRepository.getPosts().collect {
                _posts.value = it
            }
        }
    }
}
