package com.daniel.mynutricoach.ui.components.visuals

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

/**
 * Componente de carrusel que muestra una serie de imágenes de consejos saludables.
 *
 * Las imágenes se deslizan automáticamente cada 5 segundos, utilizando un `HorizontalPager`
 * de Jetpack Compose con efecto de auto-scroll.
 *
 * Este carrusel está diseñado para usarse, por ejemplo, en la parte superior de la pantalla de progreso
 * o como encabezado motivacional.
 *
 * Las imágenes se escalan con `ContentScale.Crop` y se ajustan a un alto de 200dp.
 */
@Composable
fun CarouselComponent() {
    val images = listOf(
        R.drawable.consejo1,
        R.drawable.consejo2,
        R.drawable.consejo3,
        R.drawable.consejo4,
        R.drawable.consejo5
    )

    val pagerState = rememberPagerState { images.size }

    // Auto-scroll cada 5 segundos
    LaunchedEffect(Unit) {
        while (true) {
            delay(5000)
            pagerState.animateScrollToPage((pagerState.currentPage + 1) % images.size)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp) // Se fija el tamaño dentro de la AppBar
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Image(
                painter = painterResource(images[page]),
                contentDescription = null,
                contentScale = ContentScale.Crop, // Ajusta la imagen sin distorsión
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
