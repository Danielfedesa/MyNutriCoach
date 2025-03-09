package com.daniel.mynutricoach.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.daniel.mynutricoach.R
import kotlinx.coroutines.delay

import androidx.compose.ui.layout.ContentScale


@Composable
fun CarouselComponent() { // 🔹 Eliminamos el `modifier`, ya que TopAppBar no lo acepta directamente
    val images = listOf(
        R.drawable.consejo1,
        R.drawable.consejo2,
        R.drawable.consejo3
    )

    val pagerState = rememberPagerState { images.size }

    // 🔹 Auto-scroll cada 3 segundos
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            pagerState.animateScrollToPage((pagerState.currentPage + 1) % images.size)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp) // 🔹 Se fija el tamaño dentro de la AppBar
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Image(
                painter = painterResource(images[page]),
                contentDescription = null,
                contentScale = ContentScale.Crop, // 🔹 Ajusta la imagen sin distorsión
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
