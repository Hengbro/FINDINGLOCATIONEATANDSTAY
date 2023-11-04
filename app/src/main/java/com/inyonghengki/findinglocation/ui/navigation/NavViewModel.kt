package com.inyonghengki.findinglocation.ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.inyonghengki.findinglocation.core.data.repository.AppRepository
import com.inyonghengki.findinglocation.core.data.source.remote.request.LoginRequest
import com.inyonghengki.findinglocation.core.data.source.remote.request.RegistrasiRequest
import com.inyonghengki.findinglocation.core.data.source.remote.request.TempatRequest
import com.inyonghengki.findinglocation.core.data.source.remote.request.UpdateProfileRequest
import okhttp3.MultipartBody

class NavViewModel(val repo: AppRepository): ViewModel() {

    fun cekTempat(id: Int) = repo.cekTempat(id).asLiveData()

}