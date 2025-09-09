package com.example.iwanttobelieve.ui.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.iwanttobelieve.data.model.Post
import com.example.iwanttobelieve.utils.Result

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(viewModel: FeedViewModel = viewModel()) {
    val postsState by viewModel.posts.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF000000), Color(0xFF1A1A2E))
                )
            )
    ) {
        when (postsState) {
            is Result.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF00FFFF))
                }
            }
            is Result.Success -> {
                val posts = (postsState as Result.Success<List<Post>>).data
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(posts) { post ->
                        PostItem(post)
                    }
                }
            }
            is Result.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        "Erro ao carregar feed: ${(postsState as Result.Error).exception.message}",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            else -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostItem(post: Post) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .shadow(4.dp, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF121212)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = post.authorName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00FFFF) // neon ciano
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (post.imageUrl.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(post.imageUrl),
                    contentDescription = "Imagem da postagem",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF1A1A2E)), // fundo neutro para imagem
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Text(
                text = post.description,
                fontSize = 16.sp,
                color = Color(0xFFE0E0E0) // cinza claro para contraste
            )
        }
    }
}
