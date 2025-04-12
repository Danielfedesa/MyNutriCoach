package com.daniel.mynutricoach.navigation

// Clase sellada que contiene las rutas de las pantallas de la aplicación
sealed class AppScreens (val ruta:String){
    // Registro de usuario
    object Register: AppScreens("Register")
    object Login: AppScreens("Login")
    object InitialProfile : AppScreens("InitialProfile")
    object Terms : AppScreens("Terms")
    object Privacy : AppScreens("Privacy")
    object Help : AppScreens("Help")

    // Screens del cliente
    object Progress: AppScreens("Progress")
    object Diets: AppScreens("Diets")
    object FoodDetail : AppScreens("foodDetail")
    object Appointments: AppScreens("Appointments")
    object Profile: AppScreens("Profile")
    object EditProfile: AppScreens("EditProfile")
    object EditLanguage: AppScreens("EditLanguage")
    object EditNotifications: AppScreens("EditNotifications")

    // Screens del nutricionista
    object NutriHome: AppScreens("NutriHome")
    object NutriClients: AppScreens("NutriClients")
    object NutriClientDetail : AppScreens("NutriClientDetail")
    object NutriAddAppointment : AppScreens("NutriAddAppointment")
    object NutriAppointments: AppScreens("NutriAppointments")
    object NutriProfile: AppScreens("NutriProfile")
}