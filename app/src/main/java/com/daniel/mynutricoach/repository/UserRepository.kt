package com.daniel.mynutricoach.repository

import com.daniel.mynutricoach.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository (
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    ) {

        // Obtiene los datos del usuario desde Firestore
        suspend fun getUserData(): User? {
            val userId = auth.currentUser?.uid ?: return null
            return try {
                val document = db.collection("users").document(userId).get().await()
                document.toObject(User::class.java)
            } catch (e: Exception) {
                null
            }
        }

        // Guarda los datos del usuario en Firestore
        suspend fun saveUserData(user: User): Result<Void?> {
            return try {
                db.collection("users").document(user.userId).set(user).await()
                Result.success(null)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }