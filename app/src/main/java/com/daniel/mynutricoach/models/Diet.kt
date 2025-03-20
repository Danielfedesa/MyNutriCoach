import com.daniel.mynutricoach.models.Meal
import com.google.firebase.Timestamp

data class Diet(
    val id: String = "",
    val clienteId: String = "",
    val nutricionistaId: String = "",
    val fecha: Timestamp = Timestamp.now(),
    val descripcion: String = "",
    val comidas: List<Meal> = emptyList() // Lista de comidas en la dieta
)