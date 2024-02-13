package com.dabellan.mibibliotecamusical

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PlaylistService {
    @GET(Constants.PLAYLIST_USER)
    suspend fun getPlaylistUsuario(@Path("id") id: Long): Response<MutableList<Playlist>>
}