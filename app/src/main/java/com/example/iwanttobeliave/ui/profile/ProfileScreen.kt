package com.example.iwanttobelieve.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.iwanttobelieve.utils.Result
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel()
) {
    val userState by viewModel.userState.collectAsState()
    val auth = FirebaseAuth.getInstance()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF000000), Color(0xFF1A1A2E))
                )
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        when (userState) {
            is Result.Loading -> CircularProgressIndicator(
                modifier = Modifier
                    .padding(top = 200.dp),
                color = Color(0xFF00FFFF)
            )

            is Result.Success -> {
                val user = (userState as Result.Success).data
                if (user != null) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ) {
                        // Avatar circular neon
                        Box(
                            modifier = Modifier
                                .size(140.dp)
                                .clip(CircleShape)
                                .shadow(8.dp, CircleShape)
                                .background(Color(0xFF00FFFF)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = user.name.take(1).uppercase(),
                                fontSize = 60.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }

                        Spacer(Modifier.height(24.dp))

                        // Card com informações do usuário
                        Card(
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(6.dp, RoundedCornerShape(20.dp)),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF121212)),
                            elevation = CardDefaults.cardElevation(10.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(30.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    user.name,
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF00FFFF)
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    user.email,
                                    fontSize = 18.sp,
                                    color = Color(0xFFE0E0E0)
                                )
                            }
                        }

                        Spacer(Modifier.height(36.dp))

                        // Botão Sair neon
                        Button(
                            onClick = { auth.signOut() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF00FFFF),
                                contentColor = Color.Black
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(
                                text = "Sair",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            is Result.Error -> {
                Text(
                    text = "Erro ao carregar perfil",
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 200.dp)
                )
            }

            else -> {}
        }
    }
}
