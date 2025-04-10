package com.daniel.mynutricoach.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.ui.components.BottomNavBar
import com.daniel.mynutricoach.ui.components.InfoBox
import com.daniel.mynutricoach.ui.components.ProfileOption
import com.daniel.mynutricoach.viewmodel.ProfileViewModel


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileComp(navController: NavHostController, profileViewModel: ProfileViewModel = viewModel()) {

    val userName by profileViewModel.userName.collectAsState()
    val userActualWeight by profileViewModel.userWeight.collectAsState()
    val userObjetive by profileViewModel.userObjetive.collectAsState()
    val userAge by profileViewModel.userAge.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("¿Cerrar sesión?") },
            text = { Text("¿Estás seguro de que quieres cerrar sesión?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        profileViewModel.logout(navController)
                    }
                ) {
                    Text("Cerrar sesión", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            ) {
                CenterAlignedTopAppBar(
                    title = {},
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent),
                    modifier = Modifier.fillMaxSize()
                )
            }
        },
        bottomBar = { BottomNavBar(navController, "Profile") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Perfil",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(contentAlignment = Alignment.BottomEnd) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Avatar",
                    tint = Color.LightGray,
                    modifier = Modifier.size(96.dp)
                )
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Editar",
                    tint = Color.Blue,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(24.dp)
                )
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = userName,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(16.dp)
            )

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                InfoBox("Peso actual", "${userActualWeight} Kg")
                InfoBox("Objetivo", "${userObjetive} Kg")
                InfoBox("Edad", "$userAge años")
            }

            Spacer(Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ProfileOption("Datos personales") {
                    navController.navigate("editProfile")
                }

                ProfileOption("Notificaciones") {
                    navController.navigate("editNotifications")
                }

                ProfileOption("Idioma") {
                    navController.navigate("editLanguage")
                }

                ProfileOption("Política de privacidad") {
                    navController.navigate("Privacy")
                }

                ProfileOption("Términos y condiciones") {
                    navController.navigate("Terms")
                }

                ProfileOption("Obtener ayuda") {
                    navController.navigate("Help")
                }

                Button(
                    onClick = { showDialog = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4D4D))
                ) {
                    Text("Cerrar sesión",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}