package com.inyonghengki.findinglocation

import android.os.Bundle
import com.inyonghengki.findinglocation.databinding.RegistertempatBinding
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.ui.tempat.TempatViewModel
import com.inyongtisto.myhelper.extension.setToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : MyActivity() {

    private val viewModel : TempatViewModel by viewModel()

    private var _binding: RegistertempatBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = RegistertempatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolbar, "Masukan Info Tempatmu")

        setData()
        mainButton()
    }
    private fun mainButton(){

    }
    private fun setData(){

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

}