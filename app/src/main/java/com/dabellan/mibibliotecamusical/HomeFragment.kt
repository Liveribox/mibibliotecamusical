package com.dabellan.mibibliotecamusical

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dabellan.mibibliotecamusical.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment(), OnClickListener {
    private lateinit var mBinding: FragmentHomeBinding
    private lateinit var mPlaylistAdapter: PlaylistListAdapter
    private lateinit var mPodcastAdapter: PodcastListAdapter
    private lateinit var mLinearLayout: LinearLayoutManager


    companion object {
        fun newInstance(value: String): HomeFragment {
            val fragment = HomeFragment()
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
        mBinding=FragmentHomeBinding.inflate(inflater, container, false)
        return mBinding.root

        //val value = arguments?.getString("idUser")


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViewPlaylist()
        setupRecyclerViewPodcast()
    }

    //Obtener el recycler view de playlists

    private fun setupRecyclerViewPlaylist() {

        mPlaylistAdapter = PlaylistListAdapter(this@HomeFragment)
        mLinearLayout = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)

        mBinding.recyclerViewPlaylist.apply {
            setHasFixedSize(true)
            layoutManager = mLinearLayout
            adapter = mPlaylistAdapter
        }

        val value = arguments?.getString("idUser")

        getPlaylistsUsuario(value!!.toLong())

    }

    private fun getPlaylistsUsuario(id: Long){
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(PlaylistService::class.java)

        lifecycleScope.launch {
            try {
                val result = service.getPlaylistUsuario(id)
                val playlist = result.body()!!
                mPlaylistAdapter.submitList(playlist)

            } catch (e: Exception) {

                (e as? HttpException)?.let {
                    when(it!!.code()) {
                        400 -> {
                            Snackbar.make(mBinding.root,"Error 400", Snackbar.LENGTH_SHORT).show()
                        }
                        else ->

                            Snackbar.make(mBinding.root,"Error general",Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    //Obtener la recycler view de los podcast

    private fun setupRecyclerViewPodcast() {

        mPodcastAdapter = PodcastListAdapter(this@HomeFragment)
        mLinearLayout = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)

        mBinding.recyclerViewPodcast.apply {
            setHasFixedSize(true)
            layoutManager = mLinearLayout
            adapter = mPodcastAdapter
        }

        val value = arguments?.getString("idUser")

        getPodcastsUsuario(value!!.toLong())

    }

    private fun getPodcastsUsuario(id: Long){
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(PodcastService::class.java)

        lifecycleScope.launch {
            try {
                val result = service.getPodcastUsuario(id)
                val podcast = result.body()!!
                mPodcastAdapter.submitList(podcast)

            } catch (e: Exception) {

                (e as? HttpException)?.let {
                    when(it!!.code()) {
                        400 -> {
                            Snackbar.make(mBinding.root,"Error 400", Snackbar.LENGTH_SHORT).show()
                        }
                        else ->

                            Snackbar.make(mBinding.root,"Error general",Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }



    override fun onClickCancion(cancionEntity: Cancion) {
        TODO("Not yet implemented")
    }

}