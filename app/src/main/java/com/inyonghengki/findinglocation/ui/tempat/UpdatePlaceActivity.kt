package com.inyonghengki.findinglocation.ui.tempat

import android.app.Activity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.github.drjacky.imagepicker.ImagePicker
import com.inyonghengki.findinglocation.core.data.source.model.Category
import com.inyonghengki.findinglocation.core.data.source.model.LokasiTempat
import com.inyonghengki.findinglocation.core.data.source.model.Tempat
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.databinding.ActivityUpdateTempatBinding
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.ui.category.SelectCategoryActivity
import com.inyonghengki.findinglocation.ui.lokasitempat.ListLokasiTempatActivity
import com.inyonghengki.findinglocation.ui.tempat.adapter.AddImagesPeAdapter
import com.inyonghengki.findinglocation.ui.tempat.adapter.AddImagesTeAdapter
import com.inyonghengki.findinglocation.util.Pref
import com.inyonghengki.findinglocation.util.defaultError
import com.inyonghengki.findinglocation.util.lightStatusBar
import com.inyonghengki.findinglocation.util.setFullScreen
import com.inyongtisto.myhelper.extension.extra
import com.inyongtisto.myhelper.extension.getString
import com.inyongtisto.myhelper.extension.intentActivityResult
import com.inyongtisto.myhelper.extension.isEmpty
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.showErrorDialog
import com.inyongtisto.myhelper.extension.showToast
import com.inyongtisto.myhelper.extension.toGone
import com.inyongtisto.myhelper.extension.toModel
import com.inyongtisto.myhelper.extension.toMultipartBody
import com.inyongtisto.myhelper.extension.toVisible
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class UpdatePlaceActivity : MyActivity() {

    private val viewModel : TempatViewModel by viewModel()

    private val place by extra<Tempat>()
    private var selectedCategory: Category? = null
    private var selectedLocation: LokasiTempat? = null
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

    private var _binding: ActivityUpdateTempatBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(window)
        lightStatusBar(window)
        _binding = ActivityUpdateTempatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolbar, "Update Tempat")

        setupExUI()
        mainButton()
        setupImagePe()
        setupImageTe()
    }

    private fun setupExUI(){
        binding.apply {

            val user = Pref.getUser()
            place?.let {
                val data = it.kategoriId
                tvName.text = it.nameTempat
                edtKota.setText(it.kota)
                edtJambuka.setText(it.openH)
                edtJamtutup.setText(it.closeH)
                tvEmail.text = user?.email
                tvPhone.text = user?.phone
                edtDeskripsiproduk.setText(it.deskription)
                edtKategori.setText(it.category?.name)
                edtLocation.setText(it.address?.alamat)

            }
        }
    }

    private fun setupImageTe() {

        val splitImages = place?.imageTempat?.split("|")
        if (!splitImages.isNullOrEmpty()) {
            splitImages.forEach {
                listImageTe.add(it)
            }
        }

        if (listImageTe.size < 10) {
            listImageTe.add("") // sama aja dgn empty
        } else {
            binding.btnTambahFoto.toGone()
        }

        adapterImgTe.addItems(listImageTe)
        binding.rvImage.adapter = adapterImgTe
    }

    private fun setupImagePe() {

        val splitImages = place?.imagaPemilik?.split("|")
        if (!splitImages.isNullOrEmpty()) {
            splitImages.forEach {
                listImagePe.add(it)
            }
        }

        if (listImagePe.size < 10) {
            listImagePe.add("") // sama aja dgn empty
        }

        adapterImgPe.addItems(listImagePe)
        binding.rvImageBukti.adapter = adapterImgPe
    }


    private fun mainButton(){
        binding.apply {

            btnTambahFoto.setOnClickListener{
                picImageTe()
            }
            btnLanjut.setOnClickListener {
                if (validate())update()
            }

            edtKategori.setOnClickListener {
                intentActivityResult(SelectCategoryActivity::class.java, launcherCategory)
            }

            edtLocation.setOnClickListener {
                intentActivityResult(ListLokasiTempatActivity::class.java, launcherLocation)
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

    private val launcherLocation =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val str: String? = it.data?.getStringExtra("extra")
                selectedLocation = str.toModel(LokasiTempat::class.java)
                binding.edtLocation.setText(selectedLocation?.alamat)
            }
        }


    private fun validate(): Boolean{
        binding.apply {
            if (edtKota.isEmpty())return false
            if (edtJambuka.isEmpty())return false
            if (edtJamtutup.isEmpty())return false
            if (edtDeskripsiproduk.isEmpty())return false
            if (edtKategori.isEmpty())return false
            if (edtLocation.isEmpty())return false

        }
        return true
    }

    private fun update() {
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
            id = Pref.getUser()?.tempat?.id,
            userId = Pref.getUser()?.id?:0,
            imageTempat = imageTe,
            kota = binding.edtKota.getString(),
            alamat = binding.edtLocation.getString(),
            openH = binding.edtJambuka.getString(),
            closeH = binding.edtJamtutup.getString(),
            kategoriId = selectedCategory?.id,
            kategori = binding.edtKategori.getString(),
            alamatId = selectedLocation?.id,
            imagaPemilik = imagePe,
            deskription = binding.edtDeskripsiproduk.getString()
        )

        viewModel.update(reqData).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    showToast("Berhasil merubah data tempat")
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


    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

}