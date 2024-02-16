package com.dabellan.mibibliotecamusical.Entities

import java.util.Date

data class Album(
    var id: Long = 0,
    var titulo: String,
    var imagen: String,
    var patrocinado: Boolean,
    //var fechaInicioPatrocinio: Date,
    //var fechaFinPatrocinio: Date,
    //var anyo: Date
)
