package com.dabellan.mibibliotecamusical.Services

import com.dabellan.mibibliotecamusical.Constants.Constants
import com.dabellan.mibibliotecamusical.Entities.Cancion
import retrofit2.Response
import retrofit2.http.GET

interface CancionService {
    @GET(Constants.CANCIONES_PATH)
    suspend fun getCanciones(): Response<MutableList<Cancion>>
}