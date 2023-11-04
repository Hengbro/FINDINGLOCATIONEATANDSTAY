package com.inyonghengki.findinglocation.ui.auth

import android.os.Bundle
import com.inyonghengki.findinglocation.ui.navigation.NavigationActivity
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.core.data.source.remote.request.LoginRequest
import com.inyonghengki.findinglocation.databinding.ActivityLoginBinding
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.util.Pref
import com.inyongtisto.myhelper.extension.intentActivity
import com.inyongtisto.myhelper.extension.isEmpty
import com.inyongtisto.myhelper.extension.pushActivity
import com.inyongtisto.myhelper.extension.showToast
import com.inyongtisto.myhelper.extension.toastError
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : MyActivity() {

    private val viewModel : AuthViewModel by viewModel()

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setData()
        mainButton()
    }

    private fun mainButton(){

        binding.apply {

            btnMasuk.setOnClickListener {
                login()
            }

            btnRegister.setOnClickListener {
                intentActivity(RegisterActivity::class.java)
            }

        }

    }

    private fun setData(){


    }

    private fun login(){
        if (binding.edtEmail.isEmpty()) return
        if (binding.edtPassword.isEmpty()) return

        val body = LoginRequest(
            binding.edtEmail.text.toString(),
            binding.edtPassword.text.toString()
        )
        viewModel.login(body).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    Pref.isLogin = true
                    showToast(" Hallo, Selamat datang " + it?.data?.name)
                    pushActivity(NavigationActivity::class.java)
                }
                State.ERROR -> {
                    progress.dismiss()
                    toastError(it?.message?: "Kesalahan")
                }
                State.LOADING -> {
                    progress.show()
                }
            }
        }
    }
}