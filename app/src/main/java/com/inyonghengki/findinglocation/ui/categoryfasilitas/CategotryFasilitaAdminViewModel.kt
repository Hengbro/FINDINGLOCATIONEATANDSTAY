package com.inyonghengki.findinglocation.ui.categoryfasilitas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.inyonghengki.findinglocation.core.data.repository.CategoryFasilitasAdminRepository
import com.inyonghengki.findinglocation.core.data.source.model.CategoryFasilitas

class CategotryFasilitaAdminViewModel(private val repo: CategoryFasilitasAdminRepository): ViewModel() {

    fun get() = repo.getCategoryFasilitas().asLiveData()

    fun add(data: CategoryFasilitas) = repo.addCategoryFasilitas(data).asLiveData()

    fun update(data: CategoryFasilitas) = repo.updateCategoryFasilitas(data).asLiveData()

    fun delete(id: Int?) = repo.deleteCategoryFasilitas(id).asLiveData()


}