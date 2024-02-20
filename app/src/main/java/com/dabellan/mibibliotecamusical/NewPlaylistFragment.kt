package com.dabellan.mibibliotecamusical

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.dabellan.mibibliotecamusical.Constants.Constants
import com.dabellan.mibibliotecamusical.Entities.Cancion
import com.dabellan.mibibliotecamusical.Entities.Playlist
import com.dabellan.mibibliotecamusical.Entities.User
import com.dabellan.mibibliotecamusical.Services.PlaylistService
import com.dabellan.mibibliotecamusical.Services.UserService
import com.dabellan.mibibliotecamusical.databinding.FragmentLibraryBinding
import com.dabellan.mibibliotecamusical.databinding.FragmentNewPlaylistBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date


class NewPlaylistFragment : Fragment(), OnClickListener {
    private lateinit var mBinding: FragmentNewPlaylistBinding
    val formatter = SimpleDateFormat("yyyy-MM-dd")
    val date = Date()
    //val current = formatter.format(date)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_new_playlist, container, false)

        mBinding=FragmentNewPlaylistBinding.inflate(inflater, container, false)
        mBinding.btnSave.setOnClickListener {
            agregar()
        }

        return mBinding.root
    }

    private fun agregar(){
        val title = mBinding.etName.text.toString().trim()
        val numCanc = mBinding.etNumCanciones.text.toString().trim()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(PlaylistService::class.java)

        lifecycleScope.launch {
            try {
                val result = service.getPlaylistUsuario(UsuarioApplication.usuario.id)
                val playlists = result.body()

                //checkPlaylist(playlists!!, title)

                if (checkPlaylist(playlists!!, title) == true) {

                    withContext(Dispatchers.Main) {
                        Snackbar.make(mBinding.root, "Existe playlist", Snackbar.LENGTH_SHORT).show()
                    }


                } else {
                    withContext(Dispatchers.Main) {
                        Snackbar.make(mBinding.root, "No existe playlist", Snackbar.LENGTH_SHORT)
                            .show()
                    }

                    val nuevaPlaylist = Playlist(0,title,numCanc.toInt(), date, UsuarioApplication.usuario)
                    service.crearPlaylist(UsuarioApplication.usuario.id, nuevaPlaylist)
                    Snackbar.make(mBinding.root,"Creando playlist", Snackbar.LENGTH_SHORT).show()
                }


            } catch (e: Exception) {
                (e as? HttpException)?.let {
                    when (it!!.code()) {
                        400 -> {
                            Snackbar.make(mBinding.root, "No existe playlist", Snackbar.LENGTH_SHORT)
                                .show()
                        }

                        else ->
                            Snackbar.make(mBinding.root, "No existe playlist", Snackbar.LENGTH_SHORT)
                                .show()
                    }
                }
            }
        }
    }

    private fun checkPlaylist(playlists: MutableList<Playlist>, playlist: String): Boolean {
        for (playlistdefresi in playlists) {
            if (playlistdefresi.titulo == playlist) {
                Log.i("Paylist Encontrado","$playlist")
                return true
            }

            else{
                Snackbar.make(mBinding.root,"Playlist fallida",Snackbar.LENGTH_SHORT).show()
                return false
            }

        }
        return false
    }

    override fun onClickCancion(cancionEntity: Cancion) {
        TODO("Not yet implemented")
    }

    override fun onClickPlaylist(playlistEntity: Playlist) {
        TODO("Not yet implemented")
    }


}