package com.daniel.mynutricoach.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.ui.components.CustomTextField
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun Register(navController: NavHostController, auth: FirebaseAuth, db: FirebaseFirestore) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var termsAndConditions by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 85.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Crear una cuenta",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Start)
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Crea una cuenta para comenzar",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.Start)
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Correo",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.Start)
        )

        // Campo correo con la función CustomTextField
        CustomTextField(value = email, onValueChange = { email = it }, placeholder = "ejemplo@ejemplo.com")

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Contraseña",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.Start)
        )

        // Campo contraseña con la función CustomTextField
        CustomTextField(value = password, onValueChange = { password = it }, placeholder = "Crear contraseña", isPassword = true)

        Spacer(Modifier.height(12.dp))

        // Campo contraseña con la función CustomTextField
        CustomTextField(value = confirmPassword, onValueChange = { confirmPassword = it }, placeholder = "Confirmar contraseña", isPassword = true)

        Spacer(Modifier.height(16.dp))

        // Casilla de verificación de términos y condiciones
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = termsAndConditions,
                onCheckedChange = { termsAndConditions = it }
            )

            Spacer(Modifier.width(8.dp))

            // Texto con enlaces clickeables
            val annotatedString = buildAnnotatedString {
                append("He leído y estoy de acuerdo con los ")

                // Términos y Condiciones CLICKEABLES
                pushStringAnnotation(tag = "terms", annotation = "terms")
                withStyle(style = SpanStyle(color = Color.Blue)) {
                    append("Términos y Condiciones")
                }
                pop()

                append(" y la ")

                // Política de Privacidad CLICKEABLE
                pushStringAnnotation(tag = "privacy", annotation = "privacy")
                withStyle(style = SpanStyle(color = Color.Blue)) {
                    append("Política de Privacidad")
                }
                pop()
            }

            ClickableText(
                text = annotatedString,
                onClick = { offset ->
                    annotatedString.getStringAnnotations(tag = "terms", start = offset, end = offset)
                        .firstOrNull()?.let {
                            navController.navigate("TermsScreen") // Navega a la pantalla de términos
                        }

                    annotatedString.getStringAnnotations(tag = "privacy", start = offset, end = offset)
                        .firstOrNull()?.let {
                            navController.navigate("PrivacyScreen") // Navega a la pantalla de privacidad
                        }
                }
            )
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                if (password != confirmPassword) {
                    Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                if (!termsAndConditions) {
                    Toast.makeText(context, "Debes aceptar los términos y condiciones", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userId = auth.currentUser?.uid ?: return@addOnCompleteListener

                            val user = hashMapOf("email" to email, "userId" to userId)

                            db.collection("users").document(userId)
                                .set(user)
                                .addOnSuccessListener {
                                    navController.navigate("InitialProfile") {
                                        popUpTo("Register") { inclusive = true }
                                    }
                                }
                        } else {
                            Toast.makeText(context, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            },
            enabled = termsAndConditions,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Crear Cuenta")
        }
    }
}