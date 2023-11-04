package com.inyonghengki.findinglocation.ui.navigation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.inyonghengki.findinglocation.R
import com.inyonghengki.findinglocation.databinding.ActivityNavigationBinding
import com.inyonghengki.findinglocation.ui.auth.LoginActivity
import com.inyonghengki.findinglocation.util.Pref
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("DEPRECATION")
class NavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavigationBinding
    private val viewModel : NavViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNav()
        getUser()

    }

    private fun getUser(){
        viewModel.cekTempat(Pref.getUser()?.id?: 0).observe(this) {}
    }

    private fun setupNav(){
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_navigation)

        navView.setupWithNavController(navController)
        navView.setOnItemSelectedListener {

            if (it.itemId == R.id.navigation_dashboard){

                if (Pref.isLogin){ // true atau false
                    Log.d("TAG", "Sudah login")
                    navController.navigate(it.itemId)
                } else {
                    startActivity(Intent(this, LoginActivity::class.java))
                    Log.d("TAG", "Belum login")
                    return@setOnItemSelectedListener false
                }
            } else if(it.itemId == R.id.navigation_feedback){
                if (Pref.isLogin){ // true atau false
                    Log.d("TAG", "Sudah login")
                    navController.navigate(it.itemId)
                } else {
                    startActivity(Intent(this, LoginActivity::class.java))
                    Log.d("TAG", "Belum login")
                    return@setOnItemSelectedListener false
                }
            } else if(it.itemId == R.id.navigation_notifications){
                if (Pref.isLogin){ // true atau false
                    Log.d("TAG", "Sudah login")
                    navController.navigate(it.itemId)
                } else {
                    startActivity(Intent(this, LoginActivity::class.java))
                    Log.d("TAG", "Belum login")
                    return@setOnItemSelectedListener false
                }
            }
            else {
                navController.navigate(it.itemId)
                Log.d("TAG", "onCreate: yang lain" + it.itemId)
            }

            return@setOnItemSelectedListener true
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}