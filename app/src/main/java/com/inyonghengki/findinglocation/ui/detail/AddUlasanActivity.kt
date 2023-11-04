package com.inyonghengki.findinglocation.ui.detail

import android.os.Bundle
import com.inyonghengki.findinglocation.core.data.source.model.Rating
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.databinding.ActivityAddRatingBinding
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.util.Pref
import com.inyonghengki.findinglocation.util.defaultError
import com.inyongtisto.myhelper.extension.getString
import com.inyongtisto.myhelper.extension.isEmpty
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.showErrorDialog
import com.inyongtisto.myhelper.extension.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddUlasanActivity : MyActivity() {

    private lateinit var binding: ActivityAddRatingBinding
    private val viewModel : DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRatingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolbar, "Tambah Ulasan")

        mainButton()
    }

    private fun mainButton() {

        binding.apply {

            btnSimpan.setOnClickListener {
                if (validate()) Tambah()
            }
        }
    }

    private fun validate(): Boolean{
        binding.apply {
            if (edtUlasan.isEmpty())return false
        }
        return true
    }

    private fun Tambah() {

        val id = intent.getIntExtra("id", 0)

        val reqData = Rating(
            userId = Pref.getUser()?.id?:0,
            tempatId = id,
            comfort = binding.rbComfort.rating,
            cleanliness = binding.rbCleanliness.rating,
            service = binding.rbService.rating,
            location = binding.rbLocation.rating,
            price = binding.rbPrice.rating,
            review = binding.edtUlasan.getString()
        )

        viewModel.add(reqData).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    showToast("Berhasil menambahkan ulasan")
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
