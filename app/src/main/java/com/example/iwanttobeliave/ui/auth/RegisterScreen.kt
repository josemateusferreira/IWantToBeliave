package com.example.iwanttobelieve.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.iwanttobelieve.ui.navigation.Destinations
import com.example.iwanttobelieve.utils.Result
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: AuthViewModel = viewModel()
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authState by viewModel.authState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(authState) {
        if (authState is Result.Success) {
            navController.navigate(Destinations.FEED) {
                popUpTo(Destinations.REGISTER) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Criar Conta",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 32.sp,
                    color = Color(0xFF00FFFF),
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(Modifier.height(24.dp))

            // Campo Nome
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                textStyle = TextStyle(color = Color.White),
                label = { Text("Nome") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = Color(0xFF00FFFF),
                    focusedBorderColor = Color(0xFF00FFFF),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(0xFF00FFFF),
                    unfocusedLabelColor = Color.White
                )
            )

            Spacer(Modifier.height(16.dp))

            // Campo Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                textStyle = TextStyle(color = Color.White),
                label = { Text("E-mail") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = Color(0xFF00FFFF),
                    focusedBorderColor = Color(0xFF00FFFF),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(0xFF00FFFF),
                    unfocusedLabelColor = Color.White
                )
            )

            Spacer(Modifier.height(16.dp))

            // Campo Senha
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                textStyle = TextStyle(color = Color.White),
                label = { Text("Senha") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = Color(0xFF00FFFF),
                    focusedBorderColor = Color(0xFF00FFFF),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(0xFF00FFFF),
                    unfocusedLabelColor = Color.White
                )
            )

            Spacer(Modifier.height(24.dp))

            // Botão Registrar
            Button(
                onClick = {
                    if (name.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
                        coroutineScope.launch {
                            viewModel.register(name, email, password)
                        }
                    } else {
                        viewModel.setError("Preencha todos os campos")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00FFFF))
            ) {
                Text(
                    "Registrar",
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(12.dp))

            TextButton(onClick = { navController.navigate(Destinations.LOGIN) }) {
                Text(
                    "Já tem conta? Entrar",
                    color = Color(0xFF00FFFF),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(24.dp))

            when (authState) {
                is Result.Loading -> CircularProgressIndicator(color = Color(0xFF00FFFF))
                is Result.Error -> Text(
                    text = "Erro: ${(authState as Result.Error).exception.message}",
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
                else -> {}
            }
        }
    }
}
