package com.inyonghengki.findinglocation.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.inyonghengki.findinglocation.core.data.repository.CategoryAdminRepository
import com.inyonghengki.findinglocation.core.data.source.model.Category

class CategotryAdminViewModel(private val repo: CategoryAdminRepository): ViewModel() {

    fun get() = repo.getCategory().asLiveData()

    fun add(data: Category) = repo.addCategory(data).asLiveData()

    fun update(data: Category) = repo.updateCategory(data).asLiveData()

    fun delete(id: Int?) = repo.deleteCategory(id).asLiveData()


}