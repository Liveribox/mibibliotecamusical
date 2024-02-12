package com.dabellan.mibibliotecamusical

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.dabellan.mibibliotecamusical.databinding.ActivityLoginBinding
import com.dabellan.mibibliotecamusical.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.btnLogin.setOnClickListener {
            login()
        }

        mBinding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login(){
        val user = mBinding.etUserLogin.text.toString().trim()
        val password = mBinding.etPasswordLogin.text.toString().trim()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(UserService::class.java)

        lifecycleScope.launch {
            try {
                val result = service.getUsuarios()
                val users = result.body()!!

                withContext(Dispatchers.Main) {
                    checkUser(users, user, password)
                }

            } catch (e: Exception) {
                (e as? HttpException)?.let {
                    when(it!!.code()) {
                        400 -> {
                            Snackbar.make(mBinding.root,"Error",Snackbar.LENGTH_SHORT).show()
                        }
                        else ->
                            Snackbar.make(mBinding.root,"Error",Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun checkUser(users: MutableList<User>, user: String, password: String) {
        for (userr in users) {
            if (userr.username == user) {
                Log.i("Usuario Encontrado","$user")

                if(userr.password == password){
                    Log.i("Constraseña Encontrada","$password")
                }
                Log.i("ID Usu","${userr.id}")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            else{
                Snackbar.make(mBinding.root,"Usuario Incorrecto",Snackbar.LENGTH_SHORT).show()
            }

        }
    }
}