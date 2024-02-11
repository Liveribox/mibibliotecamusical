package com.dabellan.mibibliotecamusical

import java.time.LocalDateTime
import java.util.Date

data class User (
    //var id: Long,
    var username: String,
    var password: String,
    var email: String,
    var genero: String?,
    var pais: String?,
    var fechaNacimiento: LocalDateTime,
    var codigoPostal: String?
)
