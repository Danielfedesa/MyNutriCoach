package com.daniel.mynutricoach.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.R
import com.daniel.mynutricoach.ui.components.cards.ClienteCard
import com.daniel.mynutricoach.ui.components.buttons.NutriBottomNavBar
import com.daniel.mynutricoach.ui.components.inputs.SearchBarComp
import com.daniel.mynutricoach.viewmodel.NutriClientsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutriClientsScreen(
    navController: NavHostController,
    clientesViewModel: NutriClientsViewModel = viewModel()
) {
    val clientes by clientesViewModel.clientes.collectAsState()
    val searchQuery = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.banner),
                    contentDescription = "Banner",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                CenterAlignedTopAppBar(
                    title = {},
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent),
                    modifier = Modifier.fillMaxSize()
                )
            }
        },
        bottomBar = { NutriBottomNavBar(navController, "NutriClients") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 8.dp)
        ) {
            SearchBarComp(
                query = searchQuery.value,
                onQueryChange = { searchQuery.value = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            val filteredClientes = clientes.filter {
                it.nombre.contains(searchQuery.value, ignoreCase = true) ||
                        it.apellidos.contains(searchQuery.value, ignoreCase = true) ||
                        it.email.contains(searchQuery.value, ignoreCase = true)
            }

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredClientes) { cliente ->
                    ClienteCard(cliente) {
                        navController.navigate("NutriClientDetail/${cliente.userId}")
                    }
                }
            }
        }
    }
}