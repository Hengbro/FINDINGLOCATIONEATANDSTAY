package com.inyonghengki.findinglocation.ui.slider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.inyonghengki.findinglocation.core.data.repository.SliderAdminRepository
import com.inyonghengki.findinglocation.core.data.source.remote.request.SliderRequest

class SliderAdminViewModel(private val repo: SliderAdminRepository): ViewModel() {

    fun get() = repo.getSlider().asLiveData()

    fun add(data: SliderRequest) = repo.addSlider(data).asLiveData()

    fun update(data: SliderRequest) = repo.updateSlider(data).asLiveData()

    fun delete(id: Int?) = repo.deleteSlider(id).asLiveData()


}