package com.inyonghengki.findinglocation.ui.tempat.setting

import android.os.Bundle
import com.inyonghengki.findinglocation.core.data.source.model.Tempat
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.databinding.ActivitySettingtempatBinding
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.ui.lokasitempat.ListLokasiTempatActivity
import com.inyonghengki.findinglocation.ui.navigation.NavigationActivity
import com.inyonghengki.findinglocation.ui.tempat.TempatViewModel
import com.inyonghengki.findinglocation.util.defaultError
import com.inyongtisto.myhelper.extension.intentActivity
import com.inyongtisto.myhelper.extension.pushActivity
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.showConfirmDialog
import com.inyongtisto.myhelper.extension.showErrorDialog
import com.inyongtisto.myhelper.extension.toastSuccess
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingTempatActivity : MyActivity() {

    private val viewModel : TempatViewModel by viewModel()

    private var _binding: ActivitySettingtempatBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySettingtempatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolbar, "Pengaturan")


        mainButton()
    }
    private fun mainButton(){
        binding.apply {
            LyLokasi.setOnClickListener{
                intentActivity(ListLokasiTempatActivity::class.java)
            }

            btnDelete.setOnClickListener {
                confirmDelete(item = Tempat())
            }
        }
    }

    private fun confirmDelete(item: Tempat){
        showConfirmDialog(
            "Hapus Tempat",
            "Apakah anda yakin ingin menghapus Tempat anda ?",
            "Hapus"
        ){
            onDelete(item)
        }
    }

    private fun onDelete(item: Tempat) {
        viewModel.delete(item.id).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    toastSuccess("Produk berhasil di hapus")
                    pushActivity(NavigationActivity::class.java)
                }
                State.ERROR -> {
                    showErrorDialog(it.message.defaultError())
                    progress.dismiss()
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