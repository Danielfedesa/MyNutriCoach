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

@Composable
fun CarouselComponent() {
    val images = listOf(
        R.drawable.consejo1, // Añadir imágenes a `res/drawable`
        R.drawable.consejo2,
        R.drawable.consejo3
    )

    val pagerState = rememberPagerState { images.size }
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000) // Cambia cada 3 segundos
            pagerState.scrollToPage((pagerState.currentPage + 1) % images.size)
        }
    }

    HorizontalPager(state = pagerState, modifier = Modifier.height(200.dp)) { page ->
        Image(painter = painterResource(images[page]), contentDescription = "Consejo Nutricional")
    }
}
