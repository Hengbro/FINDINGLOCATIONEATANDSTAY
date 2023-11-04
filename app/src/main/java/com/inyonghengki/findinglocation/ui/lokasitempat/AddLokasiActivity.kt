package com.inyonghengki.findinglocation.ui.lokasitempat

import android.os.Bundle
import com.inyonghengki.findinglocation.core.data.source.model.LokasiTempat
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.databinding.ActivityAddLokasitempatBinding
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.util.defaultError
import com.inyonghengki.findinglocation.util.getTempatId
import com.inyongtisto.myhelper.extension.getString
import com.inyongtisto.myhelper.extension.isEmpty
import com.inyongtisto.myhelper.extension.setOnPositionSelectedListener
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.showErrorDialog
import com.inyongtisto.myhelper.extension.showToast
import com.inyongtisto.myhelper.extension.toastSimple
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddLokasiActivity : MyActivity() {

    private val viewModel : LokasiTempatViewModel by viewModel()

    private var _binding: ActivityAddLokasitempatBinding? = null
    private val binding get() = _binding!!

    private var provinsiId: Int? = null
    private var kotaId: Int? = null
    private var kecamatanId: Int? = null
    private var provinsi: String? = null
    private var kota: String? = null
    private var kecamatan: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddLokasitempatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolbar, "Lokasi Tempat")

        setData()
        mainButton()
        setupUI()
    }

    private fun setData(){

    }

    private fun setupUI(){
        val listProvinsi = listOf("Pilih provinsi","Sumatra Utara")
        val listKota = listOf("Pilih kota", "Medan", "Samosir", "Nias Selatan", "Tapanuli", "Nias Barat","DLL")
        val listKecamatan = listOf("Pilih kecamatan", "Medan Selayang", "porsea", "Teluk Dalam", "Balige", "Gunung sitoli", "DLL")

        binding.spnProvinsi.setOnPositionSelectedListener(this, listProvinsi){
            if(it == 0){
                provinsiId = null
            }else {
                provinsiId = 10
                provinsi = listProvinsi[it]
            }
        }

        binding.spnKota.setOnPositionSelectedListener(this, listKota){
            if(it == 0){
                kotaId = null
            }else {
                kotaId = 399
                kota = listKota[it]
            }
        }
        binding.spnKecamatan.setOnPositionSelectedListener(this, listKecamatan){
            if(it == 0){
                kecamatanId = null
            }else {
                kecamatanId = 5505
                kecamatan   = listKecamatan[it]
            }
        }
    }

    private fun mainButton(){
        binding.btnSimpanalamat.setOnClickListener {
            if (validate())add()
        }

    }

    private fun validate(): Boolean{
        binding.apply {
            if (edtLebel.isEmpty())return false
            if (edtDetaillokasi.isEmpty())return false
            if (edtKodepos.isEmpty())return false

            if (provinsiId == null){
                toastSimple("Harap pilih provinsi")
                return false
            }
            if (kotaId == null){
                toastSimple("Harap pilih kota")
                return false
            }
            if (kecamatanId == null){
                toastSimple("Harap pilih kecamatan")
                return false
            }
        }
        return true
    }

    private fun add() {

        val reqData = LokasiTempat(
            tempatId = getTempatId(),
            label = binding.edtLebel.getString(),
            alamat = binding.edtDetaillokasi.getString(),
            provinsi = provinsi,
            kota = kota,
            kecamatan = kecamatan,
            provinsiId = provinsiId,
            kotaId = kotaId,
            kecamatanId = kecamatanId,
            kodepos = binding.edtKodepos.getString(),
        )

        viewModel.add(reqData).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    showToast("Berhasil menambahkan alamat")
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