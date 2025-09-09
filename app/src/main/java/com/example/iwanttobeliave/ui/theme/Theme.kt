package com.example.iwanttobelieve.ui.theme   // Pacote do tema

import androidx.compose.material3.MaterialTheme        // Tema Material 3
import androidx.compose.material3.darkColorScheme      // Esquema de cores para tema escuro
import androidx.compose.material3.lightColorScheme     // Esquema de cores para tema claro
import androidx.compose.runtime.Composable             // Anotação para funções Compose

// Define paleta de cores para tema claro
private val LightColors = lightColorScheme(
    primary = Primary,        // Cor principal
    onPrimary = Surface,      // Cor do texto/ícone sobre "primary"
    secondary = Secondary,    // Cor secundária
    background = Background,  // Cor de fundo das telas
    surface = Surface,        // Cor das superfícies (cards)
    error = Error             // Cor para erros
)

// Define paleta de cores para tema escuro (pode refinar depois)
private val DarkColors = darkColorScheme(
    primary = PrimaryLight,   // Primary levemente mais claro pro dark
    secondary = Secondary,
    error = Error
)

// Composable que aplica o tema em toda a hierarquia de UI
@Composable
fun IWantToBelieveTheme(
    darkTheme: Boolean = false,    // Você pode ligar/desligar manualmente (ou usar isSystemInDarkTheme)
    content: @Composable () -> Unit // Slot de conteúdo (suas telas)
) {
    val colors = if (darkTheme) DarkColors else LightColors // Escolhe o esquema conforme o tema

    MaterialTheme(
        colorScheme = colors,         // Aplica a paleta
        typography = AppTypography,   // Aplica tipografia definida em Type.kt
        content = content             // Renderiza o conteúdo dentro do tema
    )
}
