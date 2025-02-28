package com.daniel.mynutricoach.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun getCurrentUserEmail(): String? {
        return auth.currentUser?.email
    }
}
