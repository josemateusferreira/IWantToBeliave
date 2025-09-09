package com.example.iwanttobelieve

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.iwanttobelieve.ui.navigation.AppNavGraph
import com.example.iwanttobelieve.ui.navigation.Destinations
import com.example.iwanttobelieve.ui.theme.IWantToBelieveTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            IWantToBelieveTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    // Destino inicial baseado no estado atual do usuário
                    val startDestination = if (auth.currentUser != null) {
                        Destinations.FEED
                    } else {
                        Destinations.LOGIN
                    }

                    // Listener em tempo real para logout/login
                    LaunchedEffect(Unit) {
                        auth.addAuthStateListener { firebaseAuth ->
                            val currentUser = firebaseAuth.currentUser
                            if (currentUser == null) {
                                // Usuário deslogado: redireciona para login
                                navController.navigate(Destinations.LOGIN) {
                                    popUpTo(0) { inclusive = true }
                                }
                            }
                        }
                    }

                    AppNavGraph(navController = navController, startDestination = startDestination)
                }
            }
        }
    }
}
