package com.example.iwanttobelieve.ui.theme   // Pacote do tema

import androidx.compose.material3.Typography   // Tipografia Material 3
import androidx.compose.ui.text.TextStyle      // Estilo base de texto
import androidx.compose.ui.text.font.FontFamily // Família de fonte (usaremos padrão)
import androidx.compose.ui.text.font.FontWeight // Pesos da fonte
import androidx.compose.ui.unit.sp             // Unidade de tamanho de texto

// Define um conjunto de estilos tipográficos (padrão do Material 3, com pequenos ajustes)
val AppTypography = Typography(
    // Título grande (pode usar em cabeçalhos de tela)
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Default,  // Família padrão (pode trocar por uma custom)
        fontWeight = FontWeight.SemiBold, // Peso semi-negrito
        fontSize = 24.sp                  // Tamanho 24sp
    ),
    // Título menor
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),
    // Texto padrão
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    // Texto auxiliar (descrições, legendas)
    labelMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    )
)
