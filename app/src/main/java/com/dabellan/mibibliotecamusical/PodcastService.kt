package com.dabellan.mibibliotecamusical

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PodcastService {
    @GET(Constants.PODCASTS_USER)
    suspend fun getPodcastUsuario(@Path("id") id: Long): Response<MutableList<Podcast>>
}