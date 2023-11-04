package com.inyonghengki.findinglocation.ui.review

import android.os.Bundle
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.databinding.ActivityListDataUlasanPribadiBinding
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.ui.detail.DetailViewModel
import com.inyonghengki.findinglocation.ui.review.adapter.ReviewMySelfAdapter
import com.inyonghengki.findinglocation.util.Pref
import com.inyongtisto.myhelper.extension.intentActivity
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.toGone
import com.inyongtisto.myhelper.extension.toVisible
import com.inyongtisto.myhelper.extension.toastError
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListReviewMySelf : MyActivity() {

    private val viewModel : DetailViewModel by viewModel()
    private var adapter = ReviewMySelfAdapter (
        onClick = {
            intentActivity(UpdateUlasanActivity::class.java,it)
    }
    )

    private var _binding: ActivityListDataUlasanPribadiBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityListDataUlasanPribadiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolbar, "List Ulasan Anda")

        mainButton()
        setupAdapter()

    }

    override fun onResume() {
        getData()
        super.onResume()
    }

    private fun setupAdapter(){
        binding.rvData.adapter = adapter
    }

    private fun getData(){
        val user = Pref.getUser()?.id?:0
        viewModel.getReviewSolo(user).observe(this){
            when (it.state) {
                State.SUCCESS -> {
                    binding.progressBar.toGone()
                    binding.tvError.toGone()
                    val data = it.data ?: emptyList()
                    adapter.addItems(data)

                    if(data.isEmpty()){
                        binding.tvError.toVisible()
                    }
                }
                State.ERROR -> {
                    binding.progressBar.toGone()
                    binding.tvError.toVisible()
                    toastError(it?.message ?: "Error")

                }
                State.LOADING -> {
                    binding.progressBar.toVisible()
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