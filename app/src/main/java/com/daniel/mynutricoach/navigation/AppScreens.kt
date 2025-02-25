package com.daniel.mynutricoach.navigation

sealed class AppScreens (val ruta:String){
    object Login: AppScreens("Login")
    object Home: AppScreens("Home")
}