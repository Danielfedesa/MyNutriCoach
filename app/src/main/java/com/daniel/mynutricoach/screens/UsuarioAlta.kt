package com.daniel.mynutricoach.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.daniel.mynutricoach.viewmodel.UsuarioViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun UsuarioAlta(navController: NavController, auth: FirebaseAuth, ViewModel: UsuarioViewModel) {

    val db = FirebaseFirestore.getInstance()

    var nombre_coleccion = "usuarios"

    val dni:String by ViewModel.dni.observeAsState(initial ="")
    val nombre:String by ViewModel.nombre.observeAsState(initial ="")
    val apellido1:String by ViewModel.apellido1.observeAsState(initial ="")
    val apellido2:String by ViewModel.apellido2.observeAsState(initial ="")
    val email:String by ViewModel.email.observeAsState(initial ="")
    val telefono:Int by ViewModel.telefono.observeAsState(initial =0)
    val edad:Int by ViewModel.edad.observeAsState(initial =0)
    val peso:Double by ViewModel.peso.observeAsState(initial =0.0)
    val estatura:Double by ViewModel.estatura.observeAsState(initial =0.0)
    val sexo:String by ViewModel.sexo.observeAsState(initial ="")

    val isButtonEnable:Boolean by ViewModel.isButtonEnable.observeAsState(initial =false)

    val scrollState = rememberScrollState() // Estado de scroll

    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = MaterialTheme.shapes.small

    ){

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp)
                .padding(start = 10.dp)
                .padding(end = 10.dp)
                .verticalScroll(scrollState) // Habilitar scroll
        ){
            Text(
                text = "Alta de Usuario",
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.size(20.dp))

            OutlinedTextField(
                value = dni,
                onValueChange = { ViewModel.onCompleteFields(dni = it, nombre = nombre, apellido1 = apellido1, apellido2 = apellido2, email = email, telefono = telefono, edad = edad, peso = peso, estatura = estatura, sexo = sexo) },
                label = { Text("Introduce el DNI") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.size(5.dp))

            OutlinedTextField(
                value = nombre,
                onValueChange = { ViewModel.onCompleteFields(dni = dni, nombre = it, apellido1 = apellido1, apellido2 = apellido2, email = email, telefono = telefono, edad = edad, peso = peso, estatura = estatura, sexo = sexo) },
                label = { Text("Introduce el nombre") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.size(5.dp))

            OutlinedTextField(
                value = apellido1,
                onValueChange = { ViewModel.onCompleteFields(dni = dni, nombre = nombre, apellido1 = it, apellido2 = apellido2, email = email, telefono = telefono, edad = edad, peso = peso, estatura = estatura, sexo = sexo) },
                label = { Text("Introduce el primer apellido") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.size(5.dp))

            OutlinedTextField(
                value = apellido2,
                onValueChange = { ViewModel.onCompleteFields(dni = dni, nombre = nombre, apellido1 = apellido1, apellido2 = it, email = email, telefono = telefono, edad = edad, peso = peso, estatura = estatura, sexo = sexo) },
                label = { Text("Introduce el segundo apellido") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.size(5.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { ViewModel.onCompleteFields(dni = dni, nombre = nombre, apellido1 = apellido1, apellido2 = apellido2, email = it, telefono = telefono, edad = edad, peso = peso, estatura = estatura, sexo = sexo) },
                label = { Text("Introduce el email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.size(5.dp))

            OutlinedTextField(
                value = telefono.toString(),
                onValueChange = { ViewModel.onCompleteFields(dni = dni, nombre = nombre, apellido1 = apellido1, apellido2 = apellido2, email = email, telefono = it.toInt(), edad = edad, peso = peso, estatura = estatura, sexo = sexo) },
                label = { Text("Introduce el teléfono") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.size(5.dp))

            OutlinedTextField(
                value = edad.toString(),
                onValueChange = { ViewModel.onCompleteFields(dni = dni, nombre = nombre, apellido1 = apellido1, apellido2 = apellido2, email = email, telefono = telefono, edad = it.toInt(), peso = peso, estatura = estatura, sexo = sexo) },
                label = { Text("Introduce la edad") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.size(5.dp))

            OutlinedTextField(
                value = peso.toString(),
                onValueChange = { ViewModel.onCompleteFields(dni = dni, nombre = nombre, apellido1 = apellido1, apellido2 = apellido2, email = email, telefono = telefono, edad = edad, peso = it.toDouble(), estatura = estatura, sexo = sexo) },
                label = { Text("Introduce el peso") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.size(5.dp))

            OutlinedTextField(
                value = estatura.toString(),
                onValueChange = { ViewModel.onCompleteFields(dni = dni, nombre = nombre, apellido1 = apellido1, apellido2 = apellido2, email = email, telefono = telefono, edad = edad, peso = peso, estatura = it.toDouble(), sexo = sexo) },
                label = { Text("Introduce la estatura") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.size(5.dp))

            OutlinedTextField(
                value = sexo,
                onValueChange = { ViewModel.onCompleteFields(dni = dni, nombre = nombre, apellido1 = apellido1, apellido2 = apellido2, email = email, telefono = telefono, edad = edad, peso = peso, estatura = estatura, sexo = it) },
                label = { Text("Introduce el sexo") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.size(5.dp))

            val dato = hashMapOf(
                "dni" to dni.toString(),
                "nombre" to nombre.toString(),
                "apellido1" to apellido1.toString(),
                "apellido2" to apellido2.toString(),
                "email" to email.toString(),
                "telefono" to telefono.toString(),
                "edad" to edad.toString(),
                "peso" to peso.toString(),
                "estatura" to estatura.toString(),
                "sexo" to sexo.toString()
            )

            var mensaje_confirmacion by remember { mutableStateOf("")}

            Button(
                onClick = {
                    db.collection(nombre_coleccion)
                        .document(dni)
                        .set(dato)
                        .addOnSuccessListener {
                            mensaje_confirmacion = "Usuario dado de alta correctamente"
                        }

                        .addOnFailureListener {
                            mensaje_confirmacion = "Error al dar de alta al usuario"
                        }
                },

                // Ejemplo de ViewModel para habilitar el botón
                enabled = isButtonEnable,

                border = BorderStroke(1.dp, Color.Black)
            )
            {
                Text("Guardar")
            }
            Spacer(modifier = Modifier.size(5.dp))
            Text(text = mensaje_confirmacion)

        }
    }

}