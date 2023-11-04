package com.inyonghengki.findinglocation.ui.categoryfasilitas

import android.app.Activity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.github.drjacky.imagepicker.ImagePicker
import com.inyonghengki.findinglocation.core.data.repository.BaseViewModel
import com.inyonghengki.findinglocation.core.data.source.model.CategoryFasilitas
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.databinding.ActivityAddCategoryProductBinding
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.util.Constants
import com.inyonghengki.findinglocation.util.defaultError
import com.inyonghengki.findinglocation.util.toUrlCategory
import com.inyongtisto.myhelper.extension.extra
import com.inyongtisto.myhelper.extension.getString
import com.inyongtisto.myhelper.extension.isEmpty
import com.inyongtisto.myhelper.extension.setImagePicasso
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.showErrorDialog
import com.inyongtisto.myhelper.extension.showToast
import com.inyongtisto.myhelper.extension.toMultipartBody
import com.inyongtisto.myhelper.extension.toastWarning
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class AddCategoryFasilitasAdminActivity : MyActivity() {

    private lateinit var binding: ActivityAddCategoryProductBinding
    private val viewModel :CategotryFasilitaAdminViewModel by viewModel()
    private val viewModelBase : BaseViewModel by viewModel()
    private var fileImage: File?= null
    private val category by extra<CategoryFasilitas>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCategoryProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val title = if (category==null)"Tambah Kategori" else "Detail Kategori"
        setToolbar(binding.toolbar, title)

        setupUi()
        mainButton()
    }

    private fun setupUi(){
        category?.let {
            binding.apply {
                edtNamaproduk.setText(it.name)
                edtDeskrpsi.setText(it.description)
                btnAddFoto.setImagePicasso(it.image.toUrlCategory())
            }
        }
    }

    private fun mainButton(){

        binding.apply {

            btnSimpan.setOnClickListener {
                if (category == null){
                    if (validate()) upload()
                }else {
                        update()
                    }
                }
            btnAddFoto.setOnClickListener {
                picImage()
            }
        }
    }

    private fun picImage(){
        ImagePicker.with(this)
            .crop()
            .maxResultSize(1080, 1080, true)
            .createIntentFromDialog { launcher.launch(it) }
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val uri = it.data?.data!!
            fileImage = File(uri.path!!)
            // Use the uri to load the image
            //fileImage = File(uri.path!!)
            Picasso.get().load(fileImage!!).into(binding.btnAddFoto)
        }
    }

    private fun validate(): Boolean{
        binding.apply {
            if (edtNamaproduk.isEmpty())return false
            if (edtDeskrpsi.isEmpty())return false
            if(fileImage == null){
                toastWarning("Pilih gambar kategori")
                return false
            }
        }
        return true
    }

    private fun upload(){
        val file = fileImage.toMultipartBody()
        viewModelBase.uploadImages(Constants.pathCategory, file).observe(this){
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    val imageName = it.data
                    if (category == null){
                        Tambah(imageName)
                    } else {
                        update(imageName)
                    }
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

    private fun Tambah(imageName: String?) {

        val reqData = CategoryFasilitas(
            image = imageName,
            name = binding.edtNamaproduk.getString(),
            description = binding.edtDeskrpsi.getString(),
        )

        viewModel.add(reqData).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    showToast("Berhasil menambahkan kategori")
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

    private fun update(imageName: String? = null) {

        val reqData = CategoryFasilitas(
            id = category?.id,
            image = imageName,
            name = binding.edtNamaproduk.getString(),
            description = binding.edtDeskrpsi.getString(),
        )

        viewModel.update(reqData).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    showToast("Berhasil merubah data category")
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