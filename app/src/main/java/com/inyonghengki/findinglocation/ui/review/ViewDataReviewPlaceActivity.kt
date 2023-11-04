package com.inyonghengki.findinglocation.ui.review

import android.os.Bundle
import com.inyonghengki.findinglocation.core.data.source.model.MenuFasilitas
import com.inyonghengki.findinglocation.core.data.source.model.Rating
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.databinding.ActivityAddRatingBinding
import com.inyonghengki.findinglocation.databinding.ActivityViewRatingPlaceBinding
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.ui.detail.DetailViewModel
import com.inyonghengki.findinglocation.util.defaultError
import com.inyongtisto.myhelper.extension.extra
import com.inyongtisto.myhelper.extension.getString
import com.inyongtisto.myhelper.extension.getStringExtra
import com.inyongtisto.myhelper.extension.isEmpty
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.showErrorDialog
import com.inyongtisto.myhelper.extension.showToast
import com.inyongtisto.myhelper.extension.toModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ViewDataReviewPlaceActivity : MyActivity() {

    private lateinit var binding: ActivityViewRatingPlaceBinding

    private val ratings by extra<Rating>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewRatingPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolbar, "Ulasan Pengunjung")

        setupUi()
        mainButton()
    }

    private fun setupUi(){

        binding.apply {
            ratings?.let {
                rbComfort.rating = it.comfort!!
                rbCleanliness.rating = it.cleanliness!!
                rbService.rating = it.service!!
                rbLocation.rating = it.location!!
                rbPrice.rating = it.price!!
                edtUlasan.setText(it.review)
            }
        }
    }

    private fun mainButton(){

        binding.apply {

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

}
