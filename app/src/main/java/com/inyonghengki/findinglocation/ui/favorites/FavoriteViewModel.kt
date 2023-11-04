package com.inyonghengki.findinglocation.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.inyonghengki.findinglocation.core.data.repository.FavoriteRepository
import com.inyonghengki.findinglocation.core.data.source.model.Favorite

class FavoriteViewModel(private val repo: FavoriteRepository): ViewModel() {

    fun get(id: Int? = null) = repo.getFavorite(id).asLiveData()

    fun cek(data: Favorite) = repo.cekFavorite(data).asLiveData()

    fun add(data: Favorite) = repo.addFavorite(data).asLiveData()

    fun update(data: Favorite) = repo.updateFavorite(data).asLiveData()

    fun delete(id: Int?) = repo.deleteFavorite(id).asLiveData()

}