package com.inyonghengki.findinglocation.ui.review

import android.os.Bundle
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.databinding.ActivityListDataReviewPlaceBinding
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.ui.detail.DetailViewModel
import com.inyonghengki.findinglocation.ui.review.adapter.ReviewNoPotensiAdapter
import com.inyonghengki.findinglocation.ui.review.adapter.ReviewPotensiAdapter
import com.inyonghengki.findinglocation.util.Pref
import com.inyongtisto.myhelper.extension.intentActivity
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.toGone
import com.inyongtisto.myhelper.extension.toVisible
import com.inyongtisto.myhelper.extension.toastError
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListReviewPlaceMe : MyActivity() {

    private val viewModel : DetailViewModel by viewModel()
    private var adapterPotensi = ReviewPotensiAdapter(
        onClick = {
            intentActivity(ViewDataReviewPlaceActivity::class.java,it)
    }
    )

    private var adapterNoPotensi = ReviewNoPotensiAdapter(
        onClick = {
            intentActivity(ViewDataReviewPlaceActivity::class.java,it)
        }
    )

    private var _binding: ActivityListDataReviewPlaceBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityListDataReviewPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolbar, "List Ulasan Tempat")

        mainButton()
        setupAdapter()

    }

    override fun onResume() {
        getDataPotensi()
        getDataNoPotensi()
        super.onResume()
    }

    private fun setupAdapter(){
        binding.rvReviewpotensi.adapter = adapterPotensi
        binding.rvNotpotensi.adapter = adapterNoPotensi
    }

    private fun getDataPotensi(){
        val tempatId = Pref.getUser()?.tempat?.id
        viewModel.getReviewPotensi(tempatId).observe(this){
            when (it.state) {
                State.SUCCESS -> {
                    binding.progressBarone.toGone()
                    binding.tvErroeone.toGone()
                    val data = it.data ?: emptyList()
                    adapterPotensi.addItems(data)

                    if(data.isEmpty()){
                        binding.tvErroeone.toVisible()
                    }
                }
                State.ERROR -> {
                    binding.progressBarone.toGone()
                    binding.tvErroeone.toVisible()
                    toastError(it?.message ?: "Error")

                }
                State.LOADING -> {
                    binding.progressBarone.toVisible()
                }
            }
        }
    }

    private fun getDataNoPotensi(){
        val tempatId = Pref.getUser()?.tempat?.id
        viewModel.getReviewNoPotensi(tempatId).observe(this){
            when (it.state) {
                State.SUCCESS -> {
                    binding.progressBartwo.toGone()
                    binding.tvErrortwo.toGone()
                    val data = it.data ?: emptyList()
                    adapterNoPotensi.addItems(data)

                    if(data.isEmpty()){
                        binding.tvErrortwo.toVisible()
                    }
                }
                State.ERROR -> {
                    binding.progressBartwo.toGone()
                    binding.tvErrortwo.toGone()
                    toastError(it?.message ?: "Error")

                }
                State.LOADING -> {
                    binding.progressBartwo.toVisible()
                }
            }
        }
    }

    private fun mainButton(){

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

}