package com.daniel.mynutricoach.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Estado de autenticación
    private val _isAuthenticated = MutableStateFlow<Boolean?>(null)
    val isAuthenticated: StateFlow<Boolean?> = _isAuthenticated

    // Estado del rol del usuario
    private val _userRole = MutableStateFlow<String?>(null)
    val userRole: StateFlow<String?> = _userRole

    // Mensaje de error
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun checkSession(onNavigate: (String) -> Unit) {
        val user = auth.currentUser
        if (user != null) {
            fetchUserRole(user.uid, onNavigate) // Si hay usuario, obtenemos su rol
        } else {
            _isAuthenticated.value = false
            _errorMessage.value = null // Evita mostrar un mensaje de error constante
        }
    }

    // Inicia sesión con email y contraseña
    fun signIn(email: String, password: String, onNavigate: (String) -> Unit) {
        if (email.isEmpty() || password.isEmpty()) {
            _errorMessage.value = "Por favor, completa todos los campos"
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid ?: return@addOnCompleteListener
                    fetchUserRole(userId, onNavigate)
                } else {
                    _errorMessage.value = task.exception?.message ?: "Error desconocido"
                }
            }
    }

    // Obtiene el rol del usuario desde Firestore y navega según el tipo
    private fun fetchUserRole(userId: String, onNavigate: (String) -> Unit) {
        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val role = document.getString("role")
                    _userRole.value = role

                    when (role) {
                        "nutricionista" -> {
                            _isAuthenticated.value = true
                            onNavigate("Home")
                        }
                        "cliente" -> {
                            _isAuthenticated.value = true
                            onNavigate("Progress")
                        }
                        else -> {
                            _errorMessage.value = "Rol desconocido"
                        }
                    }
                } else {
                    _errorMessage.value = "No se encontró el usuario en Firestore"
                }
            }
            .addOnFailureListener { e ->
                _errorMessage.value = "Error al obtener datos del usuario: ${e.message}"
            }
    }

    // Recuperar la contraseña
    fun resetPassword(email: String, context: Context) {
        if (email.isEmpty()) {
            return
        }

        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }
}