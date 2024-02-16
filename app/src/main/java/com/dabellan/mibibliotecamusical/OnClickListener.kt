package com.dabellan.mibibliotecamusical

import com.dabellan.mibibliotecamusical.Entities.Cancion
import com.dabellan.mibibliotecamusical.Entities.Playlist

interface OnClickListener {
    fun onClickCancion(cancionEntity: Cancion)
    fun onClickPlaylist(playlistEntity: Playlist)

}