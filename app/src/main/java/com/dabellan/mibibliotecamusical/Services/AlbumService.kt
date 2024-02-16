package com.dabellan.mibibliotecamusical.Services

import com.dabellan.mibibliotecamusical.Constants.Constants
import com.dabellan.mibibliotecamusical.Entities.Album
import com.dabellan.mibibliotecamusical.Entities.Playlist
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumService {

    @GET(Constants.ALBUMS_USER)
    suspend fun getAlbumUsuario(@Path("id") id: Long): Response<MutableList<Album>>
}