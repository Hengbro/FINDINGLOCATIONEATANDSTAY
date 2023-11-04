package com.inyonghengki.findinglocation.ui.updateprofile

import android.app.Activity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.github.drjacky.imagepicker.ImagePicker
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.core.data.source.remote.request.UpdateProfileRequest
import com.inyonghengki.findinglocation.databinding.ActivityUpdateProfilBinding
import com.inyonghengki.findinglocation.ui.auth.AuthViewModel
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.util.Constants.USER_URL
import com.inyonghengki.findinglocation.util.Pref
import com.inyongtisto.myhelper.extension.getInitial
import com.inyongtisto.myhelper.extension.int
import com.inyongtisto.myhelper.extension.isEmpty
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.showToast
import com.inyongtisto.myhelper.extension.toMultipartBody
import com.inyongtisto.myhelper.extension.toastError
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class UpdateProfileActivity : MyActivity() {

    private val viewModel : AuthViewModel by viewModel()

    private var _binding: ActivityUpdateProfilBinding? = null
    private val binding get() = _binding!!

    private var fileImage: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUpdateProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolbar, "Edit Profil")

        setData()
        mainButton()
    }

    private fun mainButton(){

        binding.apply {

            btnSimpan.setOnClickListener {
                if (fileImage != null) {
                    upload()
                } else {
                    update()
                }
            }

            imgProfile.setOnClickListener {
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

            // Use the uri to load the image
            fileImage = File(uri.path!!)
            Picasso.get().load(fileImage!!).into(binding.imgProfile)
        }
    }

    private fun setData(){

        val user = Pref.getUser()
        if (user != null){
            binding.apply {
                edtName.setText(user.name)
                edtEmail.setText(user.email)
                edtPhone.setText(user.phone)
                tvInisial.text = user.name.getInitial()

                Picasso.get().load(USER_URL + user.image).into(binding.imgProfile)
            }
        }

    }

    private fun update() {

        if (binding.edtName.isEmpty()) return
        if (binding.edtPhone.isEmpty()) return
        if (binding.edtEmail.isEmpty()) return

        val idUser = Pref.getUser()?.id
        val body = UpdateProfileRequest(
            idUser.int(),
            name = binding.edtName.text.toString(),
            email = binding.edtEmail.text.toString(),
            phone = binding.edtPhone.text.toString()
        )

        viewModel.updateProfil(body).observe(this) {

            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    showToast("Selamat profil anda berhasil diubah " + it.data?.name)
                    onBackPressedDispatcher.onBackPressed()
                }
                State.ERROR -> {
                    progress.dismiss()
                    toastError(it.message ?: "Error")
                }
                State.LOADING -> {
                    progress.show()
                }
            }
        }
    }

    private fun upload() {
        val idUser = Pref.getUser()?.id
        val file = fileImage.toMultipartBody("image")

        viewModel.uploadImgUser(idUser, file).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    update()
                }
                State.ERROR -> {
                    toastError(it.message ?: "Error")
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