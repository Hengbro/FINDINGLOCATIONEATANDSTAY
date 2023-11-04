package com.inyonghengki.findinglocation.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.inyonghengki.findinglocation.core.data.repository.AppRepository
import com.inyonghengki.findinglocation.core.data.source.remote.request.LoginRequest
import com.inyonghengki.findinglocation.core.data.source.remote.request.RegistrasiRequest
import com.inyonghengki.findinglocation.core.data.source.remote.request.UpdateProfileRequest
import okhttp3.MultipartBody

class AuthViewModel(val repo: AppRepository): ViewModel() {

    fun login(data: LoginRequest) = repo.login(data).asLiveData()

    fun register(data: RegistrasiRequest) = repo.register(data).asLiveData()

    fun updateProfil(data: UpdateProfileRequest) = repo.updateProfil(data).asLiveData()

    fun uploadImgUser(id: Int? = null, fileImage: MultipartBody.Part? = null) = repo.uploadImgUser(id, fileImage).asLiveData()

}