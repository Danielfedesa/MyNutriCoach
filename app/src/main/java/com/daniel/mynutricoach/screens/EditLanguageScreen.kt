package com.daniel.mynutricoach.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.viewmodel.ProfileViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditLanguageComp(
    navController: NavHostController,
    profileViewModel: ProfileViewModel = viewModel()
) {
    val context = LocalContext.current // 👈 AQUÍ AÑADES ESTO

    val userName by profileViewModel.userName.collectAsState()
    var selectedLanguage by remember { mutableStateOf("Español") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Cambiar Idioma",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Text(
                            text = "<",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge.copy(fontSize = 30.sp)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Selecciona el idioma, ${userName ?: "Usuario"}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // 🔵 Selector de idioma
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = {
                        selectedLanguage = "Español"
                        Toast.makeText(context, "Idioma cambiado a Español", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedLanguage == "Español") MaterialTheme.colorScheme.primary else Color.LightGray
                    )
                ) {
                    Text("Español", fontSize = 18.sp)
                }

                Button(
                    onClick = {
                        selectedLanguage = "Inglés"
                        Toast.makeText(context, "Language changed to English", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedLanguage == "Inglés") MaterialTheme.colorScheme.primary else Color.LightGray
                    )
                ) {
                    Text("Inglés", fontSize = 18.sp)
                }
            }
        }
    }
}

