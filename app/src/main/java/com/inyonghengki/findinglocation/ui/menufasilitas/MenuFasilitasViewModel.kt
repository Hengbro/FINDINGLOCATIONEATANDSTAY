package com.inyonghengki.findinglocation.ui.menufasilitas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.inyonghengki.findinglocation.core.data.repository.MenuFasilitasRepository
import com.inyonghengki.findinglocation.core.data.source.model.MenuFasilitas
import okhttp3.MultipartBody

class MenuFasilitasViewModel(private val repo: MenuFasilitasRepository): ViewModel() {

    fun getFaAll() = repo.getMenuFasilitasAll().asLiveData()
    fun getFa() = repo.getMenuFasilitas().asLiveData()
    fun getFaDetail(id: Int? = null) = repo.getFasilitasDetail(id).asLiveData()
    fun getFaDetailAll(id: Int? = null) = repo.getFasilitasDetailAll(id).asLiveData()

    fun add(data: MenuFasilitas) = repo.addMenuFasilitas(data).asLiveData()

    fun update(data: MenuFasilitas) = repo.updateMenuFasilitas(data).asLiveData()

    fun delete(id: Int?) = repo.deleteMenuFasilitas(id).asLiveData()

    fun upload(fileImage: MultipartBody.Part? = null) = repo.uploadImageFa(fileImage).asLiveData()

}