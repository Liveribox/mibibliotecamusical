package com.dabellan.mibibliotecamusical.Services

import com.dabellan.mibibliotecamusical.Constants.Constants
import com.dabellan.mibibliotecamusical.Entities.Cancion
import com.dabellan.mibibliotecamusical.Entities.Playlist
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CancionPlaylistService {
    @GET(Constants.CANCIONES_PLAYLIST)
    suspend fun getCancionesPlaylist(@Path("id") id: Long): Response<MutableList<Cancion>>
}