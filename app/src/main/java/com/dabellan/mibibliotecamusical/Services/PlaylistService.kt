package com.dabellan.mibibliotecamusical.Services

import com.dabellan.mibibliotecamusical.Constants.Constants
import com.dabellan.mibibliotecamusical.Entities.Playlist
import com.dabellan.mibibliotecamusical.Entities.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PlaylistService {
    @GET(Constants.PLAYLIST_USER)
    suspend fun getPlaylistUsuario(@Path("id") id: Long): Response<MutableList<Playlist>>

    @POST(Constants.PLAYLIST_USER)
    suspend fun crearPlaylist(@Path("id") id: Long, @Body playlist: Playlist): Playlist
}