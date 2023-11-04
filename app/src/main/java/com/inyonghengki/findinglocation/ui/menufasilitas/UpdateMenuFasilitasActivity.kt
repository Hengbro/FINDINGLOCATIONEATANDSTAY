package com.inyonghengki.findinglocation.ui.menufasilitas

import android.app.Activity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.github.drjacky.imagepicker.ImagePicker
import com.inyonghengki.findinglocation.core.data.source.model.CategoryFasilitas
import com.inyonghengki.findinglocation.core.data.source.model.MenuFasilitas
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.databinding.ActivityAddFasilitasBinding
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.ui.categoryfasilitas.SelectCategoryFasilitasActivity
import com.inyonghengki.findinglocation.ui.menufasilitas.adapter.AddImagesFaslitasAdapter
import com.inyonghengki.findinglocation.util.defaultError
import com.inyonghengki.findinglocation.util.getTempatId
import com.inyongtisto.myhelper.extension.extra
import com.inyongtisto.myhelper.extension.getString
import com.inyongtisto.myhelper.extension.intentActivityResult
import com.inyongtisto.myhelper.extension.isEmpty
import com.inyongtisto.myhelper.extension.remove
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.showErrorDialog
import com.inyongtisto.myhelper.extension.showToast
import com.inyongtisto.myhelper.extension.toGone
import com.inyongtisto.myhelper.extension.toModel
import com.inyongtisto.myhelper.extension.toMultipartBody
import com.inyongtisto.myhelper.extension.toVisible
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class UpdateMenuFasilitasActivity : MyActivity() {

    private val viewModel : MenuFasilitasViewModel by viewModel()
    private var selectedCategory: CategoryFasilitas? = null

    private val product by extra<MenuFasilitas>()
    private val adapterImg = AddImagesFaslitasAdapter(
        onAddImage = {
            picImage()
        },
        onDeleteImage = {
            removeImage(it)
        }
    )

    private fun removeImage(index: Int){
        listImages.removeAt(index)
        adapterImg.removeAt(index)

        if (!listImages.any { it.isEmpty() } ){
            listImages.add("")
            adapterImg.addItems(listImages)
            binding.btnTambahFoto.toVisible()
        }

    }

    private var listImages = ArrayList<String>()

    private var _binding: ActivityAddFasilitasBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddFasilitasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolbar, "Update Fasilitas")

        getExtra()
        setupExUI()
        mainButton()
        setupImageProduct()
    }

    private fun getExtra(){

    }

    private fun setupExUI(){
        binding.apply {
            product?.let {
                edtNamaproduk.setText(it.name)
                edtQty.setText(it.stock.toString())
                edtDeskripsiproduk.setText(it.description)
                edtKategori.setText(it.category)

            }
        }
    }

    private fun setupImageProduct() {

        val splitImages = product?.image?.split("|")
        if (!splitImages.isNullOrEmpty()) {
            splitImages.forEach {
                listImages.add(it)
            }
        }

        if (listImages.size < 10) {
            listImages.add("") // sama aja dgn empty
        } else {
            binding.btnTambahFoto.toGone()
        }

        adapterImg.addItems(listImages)
        binding.rvImage.adapter = adapterImg
    }

    private fun mainButton(){
        binding.apply {

            btnTambahFoto.setOnClickListener{
                picImage()
            }

            btnSimpan.setOnClickListener {
                if (validate())update()
            }
            edtKategori.setOnClickListener{
                intentActivityResult(SelectCategoryFasilitasActivity::class.java, launcherCategory)
            }
        }
    }

    private val launcherCategory =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val str: String? = it.data?.getStringExtra("extra")
                selectedCategory = str.toModel(CategoryFasilitas::class.java)
                binding.edtKategori.setText(selectedCategory?.name)
            }
        }
    private fun validate(): Boolean{
        binding.apply {
            if (edtNamaproduk.isEmpty())return false
            if (edtQty.isEmpty())return false
            if (edtDeskripsiproduk.isEmpty())return false
            if (edtKategori.isEmpty())return false
        }
        return true
    }

    private fun update() {
        var images = ""
        listImages.forEach {
            if (it.isNotEmpty()) images += "$it|"

        }

        images = images.dropLast(1)

        val reqData = MenuFasilitas(
            id = product?.id,
            tempatId = getTempatId(),
            image = images,
            name = binding.edtNamaproduk.getString(),
            stock = binding.edtQty.getString().remove(",").toInt(),
            category = binding.edtKategori.getString(),
            categoryId = selectedCategory?.id,
            description = binding.edtDeskripsiproduk.getString(),
        )

        viewModel.update(reqData).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    showToast("Berhasil merubah data fasilitas")
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

    private fun picImage(){
        ImagePicker.with(this)
            .crop()
            .maxResultSize(1080, 1080, true)
            .createIntentFromDialog { launcher.launch(it) }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val uri = it.data?.data!!

            val fileImage = File(uri.path!!)
            upload(fileImage)
            // Use the uri to load the image
            //fileImage = File(uri.path!!)
            //Picasso.get().load(fileImage!!).into(binding.rvImage)
        }
    }

    private fun upload(fileImage: File){
        val file = fileImage.toMultipartBody()
        viewModel.upload(file).observe(this){
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    val tempImages = listImages.filter { image -> image.isNotEmpty() } as ArrayList
                    tempImages.add(it.data ?: "image")
                    if (tempImages.size <10) tempImages.add("")
                    else binding.btnTambahFoto.toGone()

                    listImages = tempImages
                    adapterImg.addItems(tempImages)

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