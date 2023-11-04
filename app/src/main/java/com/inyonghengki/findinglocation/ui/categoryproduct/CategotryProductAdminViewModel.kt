package com.inyonghengki.findinglocation.ui.categoryproduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.inyonghengki.findinglocation.core.data.repository.CategoryProductAdminRepository
import com.inyonghengki.findinglocation.core.data.source.model.CategoryProduct

class CategotryProductAdminViewModel(private val repo: CategoryProductAdminRepository): ViewModel() {

    fun get() = repo.getCategoryProduct().asLiveData()

    fun add(data: CategoryProduct) = repo.addCategoryProduct(data).asLiveData()

    fun update(data: CategoryProduct) = repo.updateCategoryProduct(data).asLiveData()

    fun delete(id: Int?) = repo.deleteCategoryProduct(id).asLiveData()


}