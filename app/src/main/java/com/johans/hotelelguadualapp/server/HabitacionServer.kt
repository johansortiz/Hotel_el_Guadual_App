package com.johans.hotelelguadualapp.server
import java.io.Serializable

data class HabitacionServer(
    val id: Int? = 0,
    val descripcion: String? = "",
    val estado: String? = "",
    val foto: String? = "",
    val valor: Long? = 0
) : Serializable