package com.dabellan.mibibliotecamusical

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    @GET(Constants.USERS_PATH)
    suspend fun getUsuarios(): Response<MutableList<User>>

    @GET(Constants.USER_EMAIL_PATH)
    suspend fun obtenerUsuarioPorEmail(@Path("email") email: String): Response<User>

    @POST(Constants.USERS_PATH)
    suspend fun crearUsuario(@Body usuario: User): User
}