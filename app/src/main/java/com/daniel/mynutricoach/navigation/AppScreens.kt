package com.daniel.mynutricoach.navigation

/**
 * Clase sellada que define todas las rutas de navegación disponibles en la aplicación.
 *
 * Cada objeto representa una pantalla concreta y define su ruta correspondiente,
 * utilizada para configurar la navegación en [AppNavigation].
 *
 * Agrupadas en tres bloques principales:
 * - Registro y autenticación
 * - Pantallas para usuarios cliente
 * - Pantallas para nutricionistas
 *
 * El valor [ruta] es el identificador único usado en el sistema de navegación de Compose.
 *
 * @property ruta La ruta de navegación asociada a cada pantalla.
 */
sealed class AppScreens(val ruta: String) {

    // Registro de usuario
    object Register : AppScreens("Register")
    object Login : AppScreens("Login")
    object InitialProfile : AppScreens("InitialProfile")
    object Terms : AppScreens("Terms")
    object Privacy : AppScreens("Privacy")
    object Help : AppScreens("Help")

    // Screens del cliente
    object Progress : AppScreens("Progress")
    object Diets : AppScreens("Diets")
    object FoodDetail : AppScreens("foodDetail")
    object Appointments : AppScreens("Appointments")
    object Profile : AppScreens("Profile")
    object EditProfile : AppScreens("EditProfile")
    object EditLanguage : AppScreens("EditLanguage")
    object EditNotifications : AppScreens("EditNotifications")

    // Screens del nutricionista
    object NutriHome : AppScreens("NutriHome")
    object NutriClients : AppScreens("NutriClients")
    object NutriClientDetail : AppScreens("NutriClientDetail")
    object NutriAddAppointment : AppScreens("NutriAddAppointment")
    object NutriAppointments : AppScreens("NutriAppointments")
    object NutriProfile : AppScreens("NutriProfile")
    object NutriEditProfile : AppScreens("NutriEditProfile")
    object NutriAddDiet : AppScreens("NutriAddDiet")
    object NutriViewDiet : AppScreens("NutriViewDiet")

}