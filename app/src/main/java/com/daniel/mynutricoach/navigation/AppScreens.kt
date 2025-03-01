package com.daniel.mynutricoach.navigation

sealed class AppScreens (val ruta:String){
    object Login: AppScreens("Login")
    object Home: AppScreens("Home")
    object Progress: AppScreens("Progress")
    object Register: AppScreens("Register")
    object InitialProfile : AppScreens("InitialProfile")
    object Terms : AppScreens("Terms")
    object Privacy : AppScreens("Privacy")
}