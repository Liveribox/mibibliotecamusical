package com.dabellan.mibibliotecamusical

import java.util.Date

data class Playlist(
    var id: Long = 0,
    var titulo: String,
    var numeroCanciones: Int,
    //var fechaCreacion: Date,
    var usuario: User
)
