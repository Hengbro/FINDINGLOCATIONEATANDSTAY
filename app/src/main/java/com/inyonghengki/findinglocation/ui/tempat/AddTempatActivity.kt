package com.inyonghengki.findinglocation.ui.tempat

import android.app.Activity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.github.drjacky.imagepicker.ImagePicker
import com.inyonghengki.findinglocation.core.data.source.model.Category
import com.inyonghengki.findinglocation.core.data.source.model.Tempat
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.databinding.RegistertempatBinding
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.ui.category.SelectCategoryActivity
import com.inyonghengki.findinglocation.ui.tempat.adapter.AddImagesPeAdapter
import com.inyonghengki.findinglocation.ui.tempat.adapter.AddImagesTeAdapter
import com.inyonghengki.findinglocation.util.Pref
import com.inyonghengki.findinglocation.util.defaultError
import com.inyongtisto.myhelper.extension.getString
import com.inyongtisto.myhelper.extension.intentActivity
import com.inyongtisto.myhelper.extension.intentActivityResult
import com.inyongtisto.myhelper.extension.isEmpty
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.showErrorDialog
import com.inyongtisto.myhelper.extension.showToast
import com.inyongtisto.myhelper.extension.toGone
import com.inyongtisto.myhelper.extension.toModel
import com.inyongtisto.myhelper.extension.toMultipartBody
import com.inyongtisto.myhelper.extension.toVisible
import com.inyongtisto.myhelper.extension.toastWarning
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class AddTempatActivity : MyActivity() {

    private val viewModel : TempatViewModel by viewModel()
    private var selectedCategory: Category? = null

    private var _binding: RegistertempatBinding? = null
    private val binding get() = _binding!!

    private val adapterImgTe = AddImagesTeAdapter(
        onAddImageTe = {
            picImageTe()
        },
        onDeleteImageTe = {
            removeImageTe(it)
        }
    )

    private val adapterImgPe = AddImagesPeAdapter(
        onAddImagePe = {
            picImagePe()
        },
        onDeleteImagePe = {
            removeImagePe(it)
        }
    )

    private fun removeImageTe(index: Int){
        listImageTe.removeAt(index)
        adapterImgTe.removeAt(index)

        if (!listImageTe.any { it.isEmpty() } ){
            listImageTe.add("")
            adapterImgPe.addItems(listImageTe)
            binding.btnTambahFoto.toVisible()
        }

    }

    private fun removeImagePe(index: Int){
        listImagePe.removeAt(index)
        adapterImgPe.removeAt(index)

        if (!listImagePe.any { it.isEmpty() } ){
            listImagePe.add("")
            adapterImgPe.addItems(listImagePe)
            binding.btnTambahFoto.toVisible()
        }

    }

    private var listImageTe = ArrayList<String>()
    private var listImagePe = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = RegistertempatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolbar, "Daftar Tempat")

        mainButton()
        setupImageTe()
        setupImagePe()
        setData()
    }

    private fun setupImagePe(){
        listImagePe.add("")
        adapterImgPe.addItems(listImagePe)
        binding.rvImageBukti.adapter = adapterImgPe
    }

    private fun setupImageTe(){
        listImageTe.add("")
        adapterImgTe.addItems(listImageTe)
        binding.rvImage.adapter = adapterImgTe
    }

    private fun setData(){

        val user = Pref.getUser()
        if (user != null) {
            binding.apply {
                tvEmail.text = user.email
                tvPhone.text = user.phone
                edtKota.setText(user.kota)
            }
        }

    }

    private fun mainButton(){

        binding.apply {

            btnTambahFoto.setOnClickListener{
                picImageTe()
            }

            btnLanjut.setOnClickListener {
                if (validate()) Tambah()
            }

            edtKategori.setOnClickListener {
                intentActivityResult(SelectCategoryActivity::class.java, launcherCategory)
            }

        }

    }

    private val launcherCategory =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val str: String? = it.data?.getStringExtra("extra")
                selectedCategory = str.toModel(Category::class.java)
                binding.edtKategori.setText(selectedCategory?.name)
            }
        }

    private fun picImageTe(){
        ImagePicker.with(this)
            .crop()
            .maxResultSize(1080, 1080, true)
            .createIntentFromDialog { launcherte.launch(it) }
    }

    private val launcherte = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val uri = it.data?.data!!

            val fileImage = File(uri.path!!)
            uploadTe(fileImage)
            // Use the uri to load the image
            //fileImage = File(uri.path!!)
            //Picasso.get().load(fileImage!!).into(binding.rvImage)
        }
    }

    private fun picImagePe(){
        ImagePicker.with(this)
            .crop()
            .maxResultSize(1080, 1080, true)
            .createIntentFromDialog { launcherpe.launch(it) }
    }

    private val launcherpe = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val uri = it.data?.data!!

            val fileImage = File(uri.path!!)
            uploadPe(fileImage)
            // Use the uri to load the image
            //fileImage = File(uri.path!!)
            //Picasso.get().load(fileImage!!).into(binding.rvImage)
        }
    }

    private fun validate(): Boolean{
        binding.apply {
            if (edtNamatempat.isEmpty()) return false
            if (edtKota.isEmpty()) return false
            if (edtDetalamat.isEmpty()) return false
            if (edtJambuka.isEmpty()) return false
            if (edtJamtutup.isEmpty()) return false
            if (edtDeskripsiproduk.isEmpty())return false

            if(selectedCategory == null){
                toastWarning("Pilih Kategori")
                return false
            }

        }
        return true
    }

    private fun Tambah() {

        var imageTe = ""
        listImageTe.forEach {
            if (it.isNotEmpty()) imageTe += "$it|"

        }
        imageTe = imageTe.dropLast(1)

        var imagePe = ""
        listImagePe.forEach {
            if (it.isNotEmpty()) imagePe += "$it|"

        }
        imagePe = imagePe.dropLast(1)

        val reqData = Tempat(
            userId = Pref.getUser()?.id?:0,
            imageTempat = imageTe,
            nameTempat = binding.edtNamatempat.getString(),
            kota = binding.edtKota.getString(),
            alamat = binding.edtDetalamat.getString(),
            openH = binding.edtJambuka.getString(),
            closeH = binding.edtJamtutup.getString(),
            kategoriId = selectedCategory?.id,
            kategori = binding.edtKategori.getString(),
            status = "menunggu",
            imagaPemilik = imagePe,
            deskription = binding.edtDeskripsiproduk.getString()
        )

        viewModel.store(reqData).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    val data = it.data
                    showToast("Selamat Tempat " + data?.nameTempat +" Berhasil Terdaftar Dengan Status Menunggu" )
                    intentActivity(InfoTempatActivity::class.java)

                    val user = Pref.getUser()
                    user?.tempat = Tempat(
                        id = data?.id,
                        nameTempat = data?.nameTempat,
                        kota = data?.kota,
                        openH = data?.openH,
                        closeH = data?.closeH,
                        kategori = data?.kategori,
                        deskription = data?.deskription,

                        )

                    Pref.setUser(user)
                    finish()
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

    private fun uploadPe(fileImage: File){
        val file = fileImage.toMultipartBody("imagaPemilik")
        viewModel.uploadPe(file).observe(this){
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    val tempImages = listImagePe.filter { image -> image.isNotEmpty() } as ArrayList
                    tempImages.add(it.data ?: "imagaPemilik")
                    if (tempImages.size <10) tempImages.add("")
                    else binding.btnTambahFoto.toGone()

                    listImagePe = tempImages
                    adapterImgPe.addItems(tempImages)
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

    private fun uploadTe(fileImage: File){
        val file = fileImage.toMultipartBody("imageTempat")
        viewModel.uploadTe(file).observe(this){
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    val tempImages = listImageTe.filter { image -> image.isNotEmpty() } as ArrayList
                    tempImages.add(it.data ?: "imageTempat")
                    if (tempImages.size <10) tempImages.add("")
                    else binding.btnTambahFoto.toGone()

                    listImageTe = tempImages
                    adapterImgTe.addItems(tempImages)
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