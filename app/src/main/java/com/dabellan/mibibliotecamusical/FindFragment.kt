package com.dabellan.mibibliotecamusical

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.dabellan.mibibliotecamusical.databinding.FragmentFindBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class FindFragment : Fragment(){
    private lateinit var mBinding: FragmentFindBinding
    private lateinit var mFindAdapter: FindListAdapter
    private lateinit var mGridLayout: GridLayoutManager



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // llamas a setupRecyclerView()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        // despues del setup llamas a la funcion de corrutina que te carga los datos
        // en recycler

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.CANCIONES_PATH)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CancionService::class.java)

        lifecycleScope.launch {
            try {
                val result = service.getCanciones()
                val canciones = result.body()!!

                //mFindAdapter = FindListAdapter(canciones, this@FindFragment)
                mGridLayout = GridLayoutManager(requireContext(), 1)

                mBinding.recyclerView.apply {
                    setHasFixedSize(true)
                    layoutManager = mGridLayout
                    adapter = mFindAdapter
                }

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

        /*
        mFindAdapter = FindListAdapter(mutableListOf(), this)
        mGridLayout = GridLayoutManager(this, 1)

        mBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = mGridLayout
            adapter = mFindAdapter
        }*/
    }
}