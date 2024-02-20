package com.dabellan.mibibliotecamusical

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dabellan.mibibliotecamusical.Constants.Constants
import com.dabellan.mibibliotecamusical.Entities.Cancion
import com.dabellan.mibibliotecamusical.Entities.Playlist
import com.dabellan.mibibliotecamusical.Services.CancionService
import com.dabellan.mibibliotecamusical.Services.PlaylistService
import com.dabellan.mibibliotecamusical.databinding.FragmentFindBinding
import com.dabellan.mibibliotecamusical.databinding.FragmentLibraryBinding
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LibraryFragment : Fragment(), OnClickListener {
    private lateinit var mBinding: FragmentLibraryBinding
    private lateinit var mLibraryListAdapter: LibraryListAdapter
    private lateinit var mLinearLayout: LinearLayoutManager

    companion object {
        fun newInstance(value: String): LibraryFragment {
            val fragment = LibraryFragment()
            val args = Bundle()
            args.putString("idUser", value)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding=FragmentLibraryBinding.inflate(inflater, container, false)

        mBinding.fab.setOnClickListener {
            val fragment=NewPlaylistFragment()

            val fragmentTransaction = requireFragmentManager().beginTransaction()
            fragmentTransaction.add(R.id.containerPlaylists, fragment)
            fragmentTransaction.commit()
            fragmentTransaction.addToBackStack(null)
            hideFab(false)
        }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // llamas a setupRecyclerView()
        setupRecyclerView()
    }


    private fun setupRecyclerView() {
        // despues del setup llamas a la funcion de corrutina que te carga los datos
        // en recycler

        mLibraryListAdapter = LibraryListAdapter(this@LibraryFragment)
        mLinearLayout = LinearLayoutManager(requireContext())

        mBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = mLinearLayout
            adapter = mLibraryListAdapter
        }

        val value = arguments?.getString("idUser")

        getPlaylist(value!!.toLong())
    }

    private fun getPlaylist(id: Long){
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(PlaylistService::class.java)

        lifecycleScope.launch {
            try {
                val result = service.getPlaylistUsuario(id)
                val playlists = result.body()!!
                mLibraryListAdapter.submitList(playlists)

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

    fun hideFab(isVisible: Boolean = true) {
        if (isVisible) mBinding.fab.show() else mBinding.fab.hide()
    }

    override fun onClickCancion(cancionEntity: Cancion) {
        TODO("Not yet implemented")
    }

    override fun onClickPlaylist(playlistEntity: Playlist) {
        val fragment=SongsFragment()

        val fragmentTransaction = requireFragmentManager().beginTransaction()

        val args = Bundle()
        args.putString("idPlaylist", playlistEntity.id.toString())
        fragment.arguments = args

        fragmentTransaction.add(R.id.containerPlaylists, fragment)
        fragmentTransaction.commit()
        fragmentTransaction.addToBackStack(null)
        hideFab(false)

    }
}