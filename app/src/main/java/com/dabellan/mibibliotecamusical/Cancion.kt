package com.dabellan.mibibliotecamusical

data class Cancion(
    var id: Int,
    var titulo: String,
    var duracion: String,
    var ruta: String?,
    var numeroReproducciones: Int
)
