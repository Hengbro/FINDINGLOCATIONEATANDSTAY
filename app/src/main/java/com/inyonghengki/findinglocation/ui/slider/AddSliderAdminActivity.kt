package com.inyonghengki.findinglocation.ui.slider

import android.app.Activity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.github.drjacky.imagepicker.ImagePicker
import com.inyonghengki.findinglocation.core.data.repository.BaseViewModel
import com.inyonghengki.findinglocation.core.data.source.model.Slider
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.core.data.source.remote.request.SliderRequest
import com.inyonghengki.findinglocation.databinding.ActivityAddSliderBinding
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.util.Constants
import com.inyonghengki.findinglocation.util.defaultError
import com.inyonghengki.findinglocation.util.toUrlSlider
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

class AddSliderAdminActivity : MyActivity() {

    private lateinit var binding: ActivityAddSliderBinding
    private val viewModel : SliderAdminViewModel by viewModel()
    private val viewModelBase : BaseViewModel by viewModel()
    private var fileImage: File ?= null
    private val slider by extra<Slider>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSliderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val title = if (slider==null)"Tambah Slider" else "Detail Slider"
        setToolbar(binding.toolbar, title)

        setupUi()
        mainButton()
    }

    private fun setupUi(){
        slider?.let {
            binding.apply {
                edtNamaproduk.setText(it.name)
                edtDeskripsiproduk.setText(it.description)
                btnAddFoto.setImagePicasso(it.image.toUrlSlider())
            }
        }
    }

    private fun mainButton(){

        binding.apply {

            btnSimpan.setOnClickListener {
                if (slider == null){
                    if (validate()) upload()
                }else {
                    if (fileImage != null){
                        upload()
                    } else {
                        update()
                    }
                }
            }
            btnAddFoto.setOnClickListener{
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
            if (edtDeskripsiproduk.isEmpty())return false
            if(fileImage == null){
                toastWarning("Pilih gambar")
                return false
            }
        }
        return true
    }

    private fun Tambah(imageName: String?) {

        val reqData = SliderRequest(
            image = imageName,
            name = binding.edtNamaproduk.getString(),
            description = binding.edtDeskripsiproduk.getString(),
        )

        viewModel.add(reqData).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    showToast("Berhasil menambahkan slider")
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

        val reqData = SliderRequest(
            id = slider?.id,
            image = imageName,
            name = binding.edtNamaproduk.getString(),
            description = binding.edtDeskripsiproduk.getString(),
        )

        viewModel.update(reqData).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    showToast("Berhasil merubah data slider")
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

    private fun upload(){
        val file = fileImage.toMultipartBody()
        viewModelBase.uploadImages(Constants.pathSlider, file).observe(this){
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    val imageName = it.data
                    if (slider == null){
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

}