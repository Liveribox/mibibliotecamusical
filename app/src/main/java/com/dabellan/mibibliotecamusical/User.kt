package com.dabellan.mibibliotecamusical

import java.util.Date

data class User (
    //var id: Long,
    var username: String,
    var password: String,
    var email: String,
    var genero: String?,
    var pais: String?,
    var fechaNacimiento: Date,
    var codigoPostal: String?
)
