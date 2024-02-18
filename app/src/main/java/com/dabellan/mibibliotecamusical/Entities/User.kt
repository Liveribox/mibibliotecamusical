package com.dabellan.mibibliotecamusical.Entities

import java.time.LocalDateTime
import java.util.Date

data class User (
    var id: Long = 0,
    var username: String,
    var password: String,
    var email: String,
    var genero: String,
    var pais: String,
    var fechaNacimiento: Date,
    var codigoPostal: String
)
