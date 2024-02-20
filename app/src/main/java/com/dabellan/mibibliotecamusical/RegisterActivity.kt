package com.dabellan.mibibliotecamusical

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.dabellan.mibibliotecamusical.Constants.Constants
import com.dabellan.mibibliotecamusical.Entities.User
import com.dabellan.mibibliotecamusical.Services.UserService
import com.dabellan.mibibliotecamusical.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.util.Date

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
        val genero = mBinding.etGeneroRegister.text.toString().trim()
        val pais = mBinding.etPaisRegister.text.toString().trim()
        val codigoPostal = mBinding.etCodPosRegister.text.toString().trim()
        val dia = mBinding.etFechaDiaRegister.text.toString().trim()
        val mes = mBinding.etFechaMesRegister.text.toString().trim()
        val anyo = mBinding.etFechaAnyoRegister.text.toString().trim()

        if(user.isEmpty() || password.isEmpty() || email.isEmpty() || genero.isEmpty() || pais.isEmpty() || codigoPostal.isEmpty() || dia.isEmpty() || mes.isEmpty() || dia.isEmpty()){
            Snackbar.make(mBinding.root, "Campos incompletos", Snackbar.LENGTH_SHORT).show()
            return
        }


        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(UserService::class.java)

        lifecycleScope.launch {
            try {
                val result = service.obtenerUsuarioPorEmail(email)
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

                    val nuevoUsuario = User(0,user,password,email,genero,pais, Date(anyo.toInt()-1900,mes.toInt()-1,dia.toInt()),codigoPostal)
                    service.crearUsuario(nuevoUsuario)
                    Snackbar.make(mBinding.root,"Creando usuario",Snackbar.LENGTH_SHORT).show()
                }


            } catch (e: Exception) {
                (e as? HttpException)?.let {
                    when (it!!.code()) {
                        400 -> {
                            Snackbar.make(mBinding.root, "Error 400", Snackbar.LENGTH_SHORT)
                                .show()
                        }
                        500 ->{
                            Snackbar.make(mBinding.root, "Error 500", Snackbar.LENGTH_SHORT)
                                .show()
                        }
                        else ->
                            Snackbar.make(mBinding.root, "No existe usuario", Snackbar.LENGTH_SHORT)
                                .show()
                    }
                }
            }
        }


    }
}