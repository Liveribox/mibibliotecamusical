package com.dabellan.mibibliotecamusical

import retrofit2.Response
import retrofit2.http.GET

interface CancionService {
    @GET(Constants.CANCIONES_PATH)
    suspend fun getCanciones(): Response<MutableList<Cancion>>
}