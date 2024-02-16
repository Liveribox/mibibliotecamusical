package com.dabellan.mibibliotecamusical.Entities

data class Playlist(
    var id: Long = 0,
    var titulo: String,
    var numeroCanciones: Int,
    //var fechaCreacion: Date,
    var usuario: User
)
