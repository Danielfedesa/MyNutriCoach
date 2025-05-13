# MyNutriCoach 🍎📱

## Descripción del proyecto

**MyNutriCoach** es una aplicación móvil desarrollada como Trabajo Fin de Grado del segundo curso del Ciclo Formativo de Desarrollo de Aplicaciones Multiplataforma (DAM).

La app está desarrollada en **Kotlin** utilizando **Jetpack Compose** como framework de UI y sigue el patrón de arquitectura **MVVM**, que permite una separación clara de responsabilidades y una estructura escalable. MyNutriCoach está pensada para profesionales de la nutrición y sus clientes, permitiendo gestionar dietas, citas y evolución física de forma moderna, visual e intuitiva.

Incluye funcionalidades clave como:

- Gestión de clientes, dietas, citas y progresos físicos  

- Representación gráfica del progreso con Jetpack Compose  

- Autenticación segura mediante Firebase Authentication  

- Base de datos en la nube con Cloud Firestore  

- Integración con la API externa de Nutritionix  

- Navegación dinámica con Jetpack Navigation Compose  

- Interfaz moderna, responsiva y completamente declarativa  

----------

## 📱 Tecnologías utilizadas

-   **Lenguaje:** Kotlin
    
-   **UI:** Jetpack Compose
    
-   **Arquitectura:** MVVM (Model-View-ViewModel)
    
-   **Backend:** Firebase (Authentication + Firestore)
    
-   **API externa:** Nutritionix
    
    
-   **Estado de UI:** StateFlow
    
-   **Gestión de datos:** Corrutinas y repositorios
    
-   **IDE:** Android Studio
    

----------

## 🏗 Estructura del proyecto

```rust
`📂 mynutricoach
┣ 📂 api -> Conexión con la API de Nutritionix
┣ 📂 models -> Clases de datos del dominio (User, Progress, Dieta...)
┣ 📂 navigation -> Rutas y NavHost de la app
┣ 📂 repository -> Repositorios que acceden a Firestore y API externa
┣ 📂 screens -> Pantallas principales de la aplicación
┣ 📂 ui.components -> Componentes visuales reutilizables (cards, botones, gráficos...)
┣ 📂 utils -> Funciones de utilidad (como obtenerEdad)
┣ 📂 viewmodel -> ViewModels que gestionan el estado y lógica de presentación
┣ 📂 test -> Pruebas unitarias con JUnit
┃  ┗ 📜 AgeFuncTest.kt -> Test unitario de función para calcular la edad
┗ 📜 MainActivity.kt -> Entrada principal de la app` 
```
----------

## 📌 Explicación de paquetes

-   `api`: Gestiona llamadas HTTP con Retrofit hacia Nutritionix.
    
-   `repository`: Contiene la lógica de acceso a Firestore y servicios externos.
    
-   `models`: Define las entidades del sistema como usuarios, progresos, citas o comidas.
    
-   `screens`: Incluye las distintas vistas (Home, Perfil, Dieta, Citas...) separadas por rol.
    
-   `viewmodel`: Controla el estado y acciones desde la UI.
    
-   `utils`: Funciones auxiliares, como el cálculo de edad o validaciones.
    
-   `ui.components`: Composables reutilizables como gráficas, formularios, tarjetas y más.
    
-   `test`: Contiene pruebas unitarias de funciones clave del sistema.
    

----------

## 📡 Funcionalidades principales

🔹 **Gestión integral de clientes (rol nutricionista)**

-   Visualizar clientes asignados
    
-   Registrar citas, progreso físico y dietas personalizadas
    
-   Consultar gráficas de evolución

-   Consultar agenda de citas
    

🔹 **Interfaz del cliente personalizada**

-   Acceso a su plan de comidas semanal
    
-   Ver citas programadas
    
-   Revisar su evolución física con gráficas
    

🔹 **Autenticación y perfil**

-   Registro/login seguro con Firebase
    
-   Edición del perfil y datos personales
    
-   Almacenamiento de datos en Firestore con acceso restringido
    

🔹 **Consulta nutricional externa**

-   API de Nutritionix integrada para ver calorías y nutrientes de alimentos
    
-   Consulta individual por comida asignada
    

🔹 **Diseño moderno y arquitectura limpia**

-   Compose + MVVM + StateFlow
    
-   Componentes reutilizables
    
-   Separación clara entre lógica, presentación y vista
    

🔹 **Testing**

-   Test unitario incluido para función de cálculo de edad con JUnit
    
-   Estructura preparada para añadir más pruebas
    

----------

## 🚀 Desarrollado por

**Daniel Fernández Sánchez** – 2º DAM  
Trabajo Fin de Grado 2025  