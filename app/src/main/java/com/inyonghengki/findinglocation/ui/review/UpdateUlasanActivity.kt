package com.inyonghengki.findinglocation.ui.review

import android.os.Bundle
import com.inyonghengki.findinglocation.core.data.source.model.Rating
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.databinding.ActivityAddRatingBinding
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.ui.detail.DetailViewModel
import com.inyonghengki.findinglocation.util.defaultError
import com.inyongtisto.myhelper.extension.extra
import com.inyongtisto.myhelper.extension.getString
import com.inyongtisto.myhelper.extension.isEmpty
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.showErrorDialog
import com.inyongtisto.myhelper.extension.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpdateUlasanActivity : MyActivity() {

    private lateinit var binding: ActivityAddRatingBinding

    private val viewModel : DetailViewModel by viewModel()
    private val ratings by extra<Rating>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRatingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolbar, "Update Ulasan")

        setupUi()
        mainButton()
    }

    private fun setupUi(){

        binding.apply {
            ratings?.let {
                rbComfort.rating = it.comfort?.toFloat() ?: 0.0f
                rbCleanliness.rating = it.cleanliness?.toFloat() ?: 0.0f
                rbService.rating = it.service?.toFloat() ?: 0.0f
                rbLocation.rating = it.location?.toFloat() ?: 0.0f
                rbPrice.rating = it.price?.toFloat() ?: 0.0f
                edtUlasan.setText(it.review)
            }
        }
    }

    private fun mainButton(){

        binding.apply {

            btnSimpan.setOnClickListener {
                if (validate()) update()
            }
        }
    }

    private fun validate(): Boolean{
        binding.apply {
            if (edtUlasan.isEmpty())return false
        }
        return true
    }


    private fun update() {

        val reqData = Rating(
            id = ratings?.id,
            comfort = binding.rbComfort.rating,
            cleanliness = binding.rbCleanliness.rating,
            service = binding.rbService.rating,
            location = binding.rbLocation.rating,
            price = binding.rbPrice.rating,
            review = binding.edtUlasan.getString()
        )

        viewModel.update(reqData).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    showToast("Berhasil merubah data ulasan")
                    onBackPressedDispatcher.onBackPressed()
                }
                State.ERROR -> {
                    progress.dismiss()
                    showErrorDialog(it.message.defaultError())
                }
                State.LOADING -> {
                    progress.show()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

}
