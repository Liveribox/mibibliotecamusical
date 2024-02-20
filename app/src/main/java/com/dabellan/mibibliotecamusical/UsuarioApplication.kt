package com.dabellan.mibibliotecamusical

import android.app.Application
import com.dabellan.mibibliotecamusical.Entities.User

class UsuarioApplication: Application() {
    companion object {
        lateinit var usuario: User
    }
}