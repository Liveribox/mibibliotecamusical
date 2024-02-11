package com.dabellan.mibibliotecamusical

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.dabellan.mibibliotecamusical.databinding.ActivityLoginBinding
import com.dabellan.mibibliotecamusical.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime

class RegisterActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.btnRegister.setOnClickListener {
            register()
        }
    }

    private fun register() {
        val user = mBinding.etUserRegister.text.toString().trim()
        val password = mBinding.etPasswordRegister.text.toString().trim()
        val email = mBinding.etEmailRegister.text.toString().trim()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(UserService::class.java)

        lifecycleScope.launch {
            try {
                val result = service.obtenerUsuarioPorEmail(1)
                val usuario = result.body()

                if (usuario != null) {

                    withContext(Dispatchers.Main) {
                        Snackbar.make(mBinding.root, "Existe usuario", Snackbar.LENGTH_SHORT).show()
                    }


                } else {
                    withContext(Dispatchers.Main) {
                        Snackbar.make(mBinding.root, "No existe usuario", Snackbar.LENGTH_SHORT)
                            .show()
                    }

                    /*val nuevoUsuario = User(user,password,email,null,null, LocalDateTime.now(),null)
                    service.crearUsuario(nuevoUsuario)
                    Snackbar.make(mBinding.root,"Creando usuario",Snackbar.LENGTH_SHORT).show()*/
                }


            } catch (e: Exception) {
                (e as? HttpException)?.let {
                    when (it!!.code()) {
                        400 -> {
                            //updateUI(getString(R.string.main_error_server))
                            Snackbar.make(mBinding.root, "No existe usuario", Snackbar.LENGTH_SHORT)
                                .show()
                        }

                        else ->
                            //updateUI(getString(R.string.main_error_response))
                            Snackbar.make(mBinding.root, "No existe usuario", Snackbar.LENGTH_SHORT)
                                .show()
                    }
                }
            }
        }


    }
}