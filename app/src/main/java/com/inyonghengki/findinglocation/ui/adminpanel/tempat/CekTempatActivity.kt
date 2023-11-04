package com.inyonghengki.findinglocation.ui.adminpanel.tempat

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.inyonghengki.findinglocation.core.data.source.model.Tempat
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.databinding.ActivityKonfirmasiPlaceBinding
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.ui.tempat.TempatViewModel
import com.inyonghengki.findinglocation.ui.tempat.adapter.ImageTempatSliderAdapter
import com.inyonghengki.findinglocation.util.defaultError
import com.inyongtisto.myhelper.extension.extra
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.showErrorDialog
import com.inyongtisto.myhelper.extension.showToast
import com.inyongtisto.myhelper.extension.toGone
import com.inyongtisto.myhelper.extension.toVisible
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("SetTextI18n")
class CekTempatActivity : MyActivity() {

    private var _binding: ActivityKonfirmasiPlaceBinding? = null
    private val binding get() = _binding!!

    private val viewModel : TempatViewModel by viewModel()
    private val tempat by extra<Tempat>()
    private val adapterTempat = ImageTempatSliderAdapter()
    private val adapterPemilik = ImageTempatSliderAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityKonfirmasiPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolbar, "Cek Tempat")

        mainButton()
        setAdapter()
        setUpData()

    }

    private fun mainButton(){
        binding.apply {

            lyCall.setOnClickListener {
                callNumber()
            }

            lyEmail.setOnClickListener {
                sendEmail()
            }

            tvDeskAll.setOnClickListener {
                lyDeskripsiAll.toVisible()
            }

            imgCloseDesk.setOnClickListener {
                lyDeskripsiAll.toGone()
            }

            btnKonfirmasi.setOnClickListener {
                update()
            }

        }
    }

    private fun setAdapter(){
        binding.apply {
            val imageTempat = tempat?.imageTempat?:""
            val splitImageTempat = imageTempat.split("|")
            adapterTempat.addItems(splitImageTempat)
            sliderImage.adapter = adapterTempat

            val imagePemilik = tempat?.imagaPemilik?:""
            val splitImagePemilik = imagePemilik.split("|")
            adapterTempat.addItems(splitImagePemilik)
            sliderImgPemilik.adapter = adapterPemilik
        }
    }

    private fun callNumber() {
        val phoneNumber = binding.tvNotlp.text.toString()
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("tel:$phoneNumber")

        if (dialIntent.resolveActivity(packageManager) != null) {
            startActivity(dialIntent)
        } else {
            Toast.makeText(this, "Tidak ada aplikasi telepon yang tersedia.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun sendEmail(){
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto:")
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(binding.tvEmail.text.toString()))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject of the email")
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Isi pesan email")

        if (emailIntent.resolveActivity(packageManager) != null){
            startActivity(emailIntent)
        }

    }

    private fun setUpData(){

        binding.apply {
            tempat?.let {
                tvNama.text = it.nameTempat
                tvKategori.text = it.kategori
                tvJambuka.text = it.openH +" - " +it.closeH
                tvNotlp.text = it.user?.phone
                tvEmail.text = it.user?.email
                tvKategori.text = it.kategori ?: "Lainnya"
                tvAlamat.text = it.alamat ?: "kota Medan"
                tvDeskripsi.text = it.deskription
                tvDeskAll.text = it.deskription
            }
        }
    }

    private fun update() {

        val reqData = Tempat(
            isActive = true,
            status = "Accept"
        )

        viewModel.updateConfirmation(tempat?.id, reqData).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    showToast("Berhasil mengkonfirmasi data")
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