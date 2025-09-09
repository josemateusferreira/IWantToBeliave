package com.example.iwanttobelieve.ui.post

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.iwanttobelieve.ui.navigation.Destinations
import com.example.iwanttobelieve.utils.Result

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewPostScreen(
    navController: NavController,
    viewModel: PostViewModel = viewModel()
) {
    var description by remember { mutableStateOf("") }

    val defaultImageUrl = "https://sejafranqueado.aguadecheiro.com.br/wp-content/uploads/2024/08/Ratinho-1.jpg"
    val postState by viewModel.postState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF000000), Color(0xFF1A1A2E))
                )
            )
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Nova Postagem",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00FFFF)
                )
            )

            Spacer(Modifier.height(24.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF121212)),
                elevation = CardDefaults.cardElevation(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        textStyle = TextStyle(color = Color.White),
                        label = { Text("Descrição") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            cursorColor = Color(0xFF00FFFF),
                            focusedBorderColor = Color(0xFF00FFFF),
                            unfocusedBorderColor = Color.Gray,
                            focusedLabelColor = Color(0xFF00FFFF),
                            unfocusedLabelColor = Color.White
                        )
                    )

                    Spacer(Modifier.height(16.dp))

                    Image(
                        painter = rememberAsyncImagePainter(defaultImageUrl),
                        contentDescription = "Imagem da postagem",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(Modifier.height(16.dp))

                    Button(
                        onClick = { if (description.isNotBlank()) viewModel.createPost(description, defaultImageUrl) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00FFFF)),
                        enabled = description.isNotBlank()
                    ) {
                        Text(
                            "Publicar",
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            when (postState) {
                is Result.Loading -> CircularProgressIndicator(color = Color(0xFF00FFFF))
                is Result.Success -> {
                    LaunchedEffect(Unit) {
                        navController.navigate(Destinations.FEED) {
                            popUpTo(Destinations.FEED) { inclusive = true }
                        }
                    }
                }
                is Result.Error -> Text(
                    "Erro: ${(postState as Result.Error).exception.message}",
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
                else -> {}
            }
        }
    }
}
