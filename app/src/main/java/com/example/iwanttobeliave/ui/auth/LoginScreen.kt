package com.example.iwanttobelieve.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.iwanttobelieve.ui.navigation.Destinations
import com.example.iwanttobelieve.utils.Result
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState by viewModel.authState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(authState) {
        if (authState is Result.Success) {
            navController.navigate(Destinations.FEED) {
                popUpTo(Destinations.LOGIN) { inclusive = true }
            }
        }
    }

    val appImageUrl =
        "https://cdn11.bigcommerce.com/s-uy50rek/images/stencil/1280x1280/products/2125/12370/iwanttobelieve_v1_3__94790.1532884949.jpg?c=2"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(Modifier.height(48.dp))

            // Logo UFO circular
            Image(
                painter = rememberAsyncImagePainter(appImageUrl),
                contentDescription = "Logo I Want To Believe",
                modifier = Modifier
                    .size(120.dp)      // tamanho do ícone
                    .clip(CircleShape),// formato circular
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(32.dp))

            // Campo de e-mail
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                textStyle = TextStyle(color = Color.White),
                label = { Text("E-mail") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = Color(0xFF00FFFF),
                    focusedBorderColor = Color(0xFF00FFFF),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(0xFF00FFFF),
                    unfocusedLabelColor = Color.White
                )
            )

            Spacer(Modifier.height(12.dp))

            // Campo de senha
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                textStyle = TextStyle(color = Color.White),
                label = { Text("Senha") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = Color(0xFF00FFFF),
                    focusedBorderColor = Color(0xFF00FFFF),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(0xFF00FFFF),
                    unfocusedLabelColor = Color.White
                )
            )

            Spacer(Modifier.height(24.dp))

            // Botão Entrar
            Button(
                onClick = {
                    coroutineScope.launch {
                        if (email.isNotBlank() && password.isNotBlank()) {
                            viewModel.login(email, password)
                        } else {
                            viewModel.setError("Preencha todos os campos")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00FFFF))
            ) {
                Text(
                    "Entrar",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(16.dp))

            TextButton(onClick = { navController.navigate(Destinations.REGISTER) }) {
                Text("Criar conta", color = Color(0xFF00FFFF), fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(24.dp))

            when (authState) {
                is Result.Loading -> CircularProgressIndicator(color = Color(0xFF00FFFF))
                is Result.Error -> Text(
                    text = "Erro: ${(authState as Result.Error).exception.message}",
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
                else -> {}
            }
        }
    }
}
