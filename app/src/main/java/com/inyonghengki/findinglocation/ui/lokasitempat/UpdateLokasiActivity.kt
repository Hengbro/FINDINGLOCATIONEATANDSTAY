package com.inyonghengki.findinglocation.ui.lokasitempat

import android.os.Bundle
import com.inyonghengki.findinglocation.core.data.source.model.LokasiTempat
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.databinding.ActivityAddLokasitempatBinding
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.util.defaultError
import com.inyonghengki.findinglocation.util.getTempatId
import com.inyongtisto.myhelper.extension.getString
import com.inyongtisto.myhelper.extension.getStringExtra
import com.inyongtisto.myhelper.extension.isEmpty
import com.inyongtisto.myhelper.extension.setOnPositionSelectedListener
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.showErrorDialog
import com.inyongtisto.myhelper.extension.showToast
import com.inyongtisto.myhelper.extension.toModel
import com.inyongtisto.myhelper.extension.toastSimple
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpdateLokasiActivity : MyActivity() {

    private val viewModel : LokasiTempatViewModel by viewModel()

    private var _binding: ActivityAddLokasitempatBinding? = null
    private val binding get() = _binding!!

    private var provinsiId: Int? = null
    private var kotaId: Int? = null
    private var kecamatanId: Int? = null
    private var provinsi: String? = null
    private var kota: String? = null
    private var kecamatan: String? = null

    private var lokasi = LokasiTempat()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddLokasitempatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolbar, "Update Lokasi")

        getExtra()
        mainButton()
        setupUI()
    }

    private fun getExtra(){

        val extra = getStringExtra()
        lokasi = extra.toModel(LokasiTempat::class.java) ?: LokasiTempat()

        binding.apply {
            edtLebel.setText(lokasi.label)
            edtDetaillokasi.setText(lokasi.alamat)
            edtKodepos.setText(lokasi.kodepos)
        }
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

        binding.apply {
            val indexProv = listProvinsi.indexOfFirst { it == lokasi.provinsi }
            spnProvinsi.setSelection(indexProv)

            val indexKota = listKota.indexOfFirst { it == lokasi.kota }
            spnKota.setSelection(indexKota)

            val indexKec= listKecamatan.indexOfFirst { it == lokasi.kecamatan }
            spnKecamatan.setSelection(indexKec)
        }

    }

    private fun mainButton(){
        binding.btnSimpanalamat.setOnClickListener {
            if (validate())update()
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

    private fun update() {

        val reqData = LokasiTempat(
            id = lokasi.id,
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

        viewModel.update(reqData).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    showToast("Berhasil merubah data lokasi")
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