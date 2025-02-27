package com.daniel.mynutricoach.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daniel.mynutricoach.models.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UsuarioViewModel: ViewModel(){

    private val _dni = MutableLiveData<String>()
    val dni: LiveData<String> = _dni

    private val _nombre = MutableLiveData<String>()
    val nombre: LiveData<String> = _nombre

    private val _apellido1 = MutableLiveData<String>()
    val apellido1: LiveData<String> = _apellido1

    private val _apellido2 = MutableLiveData<String>()
    val apellido2: LiveData<String> = _apellido2

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _telefono = MutableLiveData<Int>()
    val telefono: LiveData<Int> = _telefono

    private val _edad = MutableLiveData<Int>()
    val edad: LiveData<Int> = _edad

    private val _peso = MutableLiveData<Double>()
    val peso: LiveData<Double> = _peso

    private val _estatura = MutableLiveData<Double>()
    val estatura: LiveData<Double> = _estatura

    private val _sexo = MutableLiveData<String>()
    val sexo: LiveData<String> = _sexo

    private val _usuarios = MutableStateFlow<List<Usuario>>(emptyList())
    val usuarios: StateFlow<List<Usuario>> = _usuarios

    private val _isButtonEnable = MutableLiveData<Boolean>()
    val isButtonEnable: LiveData<Boolean> = _isButtonEnable

    fun onCompleteFields(dni: String, nombre: String, apellido1: String, apellido2: String, email: String, telefono: Int, edad: Int, peso: Double, estatura: Double, sexo: String){
        _dni.value = dni
        _nombre.value = nombre
        _apellido1.value = apellido1
        _apellido2.value = apellido2
        _email.value = email
        _telefono.value = telefono
        _edad.value = edad
        _peso.value = peso
        _estatura.value = estatura
        _sexo.value = sexo
        _isButtonEnable.value = enableButton(dni, nombre, apellido1, apellido2, email, telefono, edad, peso, estatura, sexo)
    }

    fun enableButton (dni:String, nombre: String, apellido1: String, apellido2: String, email: String, telefono: Int, edad: Int, peso: Double, estatura: Double, sexo: String)
    = dni.isNotEmpty() && nombre.isNotEmpty() && apellido1.isNotEmpty() && apellido2.isNotEmpty() && email.isNotEmpty() && telefono != 0 && edad != 0 && peso != 0.0 && estatura != 0.0 && sexo.isNotEmpty()

}