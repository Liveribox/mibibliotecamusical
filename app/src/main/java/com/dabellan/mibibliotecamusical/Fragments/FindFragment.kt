package com.dabellan.mibibliotecamusical.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dabellan.mibibliotecamusical.Adapters.FindListAdapter
import com.dabellan.mibibliotecamusical.Constants.Constants
import com.dabellan.mibibliotecamusical.Entities.Cancion
import com.dabellan.mibibliotecamusical.Entities.Playlist
import com.dabellan.mibibliotecamusical.OnClickListener
import com.dabellan.mibibliotecamusical.Services.CancionService
import com.dabellan.mibibliotecamusical.databinding.FragmentFindBinding
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class FindFragment : Fragment(), OnClickListener {
    private lateinit var mBinding: FragmentFindBinding
    private lateinit var mFindAdapter: FindListAdapter
    private lateinit var mLinearLayout: LinearLayoutManager
    private lateinit var listaCanciones: MutableList<Cancion>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding=FragmentFindBinding.inflate(inflater, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // llamas a setupRecyclerView()
        setupRecyclerView()

        mBinding.etBuscador.addTextChangedListener {filtracion ->
            Log.i("XXXXX",filtracion.toString())
            buscarCanciones(filtracion.toString().trim())
        }
    }

    private fun setupRecyclerView() {
        // despues del setup llamas a la funcion de corrutina que te carga los datos
        // en recycler

        mFindAdapter = FindListAdapter(this@FindFragment)
        mLinearLayout = LinearLayoutManager(requireContext())

        mBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = mLinearLayout
            adapter = mFindAdapter
        }

        getCanciones()

    }

    private fun getCanciones(){
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CancionService::class.java)

        lifecycleScope.launch {
            try {
                val result = service.getCanciones()
                val canciones = result.body()!!
                listaCanciones = canciones.toMutableList()
                mFindAdapter.submitList(canciones)

            } catch (e: Exception) {

                (e as? HttpException)?.let {
                    when(it!!.code()) {
                        400 -> {
                            //updateUI(getString(R.string.main_error_server))
                            //Toast.makeText(this@FindFragment,"Error", Toast.LENGTH_SHORT)
                        }
                        //else ->
                        //updateUI(getString(R.string.main_error_response))
                        //Toast.makeText(this@FindFragment,"Error", Toast.LENGTH_SHORT)
                    }
                }
            }
        }
    }
    
    private fun buscarCanciones(cancionesTexto : String){
        if(::listaCanciones.isInitialized){
            var filtrarCanciones = listaCanciones.filter { cancionn ->
                cancionn.titulo.contains(cancionesTexto, ignoreCase = true)
            }
            mFindAdapter.submitList(filtrarCanciones)

        }
    }

    override fun onClickCancion(cancionEntity: Cancion) {
        TODO("Not yet implemented")
    }

    override fun onClickPlaylist(playlistEntity: Playlist) {
        TODO("Not yet implemented")
    }


}