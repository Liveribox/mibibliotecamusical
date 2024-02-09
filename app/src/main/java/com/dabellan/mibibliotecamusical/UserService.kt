package com.dabellan.mibibliotecamusical

import retrofit2.Response
import retrofit2.http.GET

interface UserService {
    @GET(Constants.USERS_PATH)
    suspend fun getUsuarios(): Response<MutableList<User>>
}