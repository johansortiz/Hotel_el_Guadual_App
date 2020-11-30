package com.johans.hotelelguadualapp.server

data class ProductosServer(
    val id: Int? = 0,
    val descripcion: String? = "",
    val cantidad: Int? = 0,
    val foto: String? = "",
    val valor: Long? = 0
)