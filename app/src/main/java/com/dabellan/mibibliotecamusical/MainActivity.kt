package com.dabellan.mibibliotecamusical

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.dabellan.mibibliotecamusical.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView


    //MVVM
    /*
    private lateinit var mFindAdapter: FindListAdapter
    private lateinit var mGridLayout: GridLayoutManager*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        //setContentView(R.layout.activity_main)
        //setupRecyclerView()

        val value = intent.getStringExtra("idUser")
        val homeFragment = HomeFragment.newInstance(value!!)

        initcomponents()
        initListeners()
        replaceFragment(homeFragment)

    }

    private fun initListeners() {
        val value = intent.getStringExtra("idUser")
        val homeFragment = HomeFragment.newInstance(value!!)

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_home -> {
                    replaceFragment(homeFragment)
                    true
                }

                R.id.bottom_find -> {
                    replaceFragment(FindFragment())
                    true
                }

                R.id.bottom_library -> {
                    replaceFragment(LibraryFragment())
                    true
                }

                else -> false
            }
        }

    }

        private fun replaceFragment(fragment: Fragment) {
            supportFragmentManager.beginTransaction().replace(R.id.frameContainer, fragment).commit()
        }

        private fun initcomponents() {
            bottomNavigationView = findViewById(R.id.bottom_navigation)
        }
}