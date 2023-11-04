package com.inyonghengki.findinglocation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.inyonghengki.findinglocation.core.data.repository.AppRepository
import com.inyonghengki.findinglocation.core.data.source.local.DummyData
import com.inyonghengki.findinglocation.core.data.source.model.Slider

class HomeViewModel (private val repo: AppRepository): ViewModel() {

    fun getHome() = repo.getHome().asLiveData()
    fun getNew() = repo.getNew().asLiveData()
    fun getSlider() = repo.getSlider().asLiveData()
    fun getPlaceAge(userId: Int?  = null) = repo.getPlaceAge(userId).asLiveData()
    fun getPlaceByFas(id: Int?  = null) = repo.getPlaceByFas(id).asLiveData()
    fun getCate() = repo.getCate().asLiveData()

    val listSlider : LiveData<List<Slider>> = MutableLiveData<List<Slider>>().apply {
      value = DummyData.listSlider
    }

    //val listCategory : LiveData<List<Category>> = MutableLiveData<List<Category>>().apply {
      //  value = DummyData.listCategory
    //}

    /*val listTempat : LiveData<List<Tempat>> = MutableLiveData<List<Tempat>>().apply {
        value = DummyData.listTempat
    }*/

}