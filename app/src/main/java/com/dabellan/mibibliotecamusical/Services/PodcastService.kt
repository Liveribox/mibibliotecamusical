package com.dabellan.mibibliotecamusical.Services

import com.dabellan.mibibliotecamusical.Constants.Constants
import com.dabellan.mibibliotecamusical.Entities.Podcast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PodcastService {
    @GET(Constants.PODCASTS_USER)
    suspend fun getPodcastUsuario(@Path("id") id: Long): Response<MutableList<Podcast>>
}