package com.daniel.mynutricoach.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.R
import com.daniel.mynutricoach.ui.components.buttons.BottomNavBar
import com.daniel.mynutricoach.ui.components.cards.NutrientCard
import com.daniel.mynutricoach.viewmodel.FoodDetailViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ClientFoodDetailScreen(
    navController: NavHostController,
    foodDetailViewModel: FoodDetailViewModel = viewModel(),
    tipo: String,
    alimentos: List<String>
) {
    val nutrientes by foodDetailViewModel.nutrientes.collectAsState()

    LaunchedEffect(true) {
        foodDetailViewModel.cargarNutrientes(alimentos)
    }

    Scaffold(
        topBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.banner),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        },
        bottomBar = { BottomNavBar(navController, "Diets") }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Información nutricional",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (nutrientes.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                nutrientes.forEach { info ->
                    NutrientCard(info)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
