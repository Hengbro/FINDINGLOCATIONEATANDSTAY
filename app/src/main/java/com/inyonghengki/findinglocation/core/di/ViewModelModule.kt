package com.inyonghengki.findinglocation.core.di

import com.inyonghengki.findinglocation.core.data.repository.BaseViewModel
import com.inyonghengki.findinglocation.ui.akuns.AkunsViewModel
import com.inyonghengki.findinglocation.ui.auth.AuthViewModel
import com.inyonghengki.findinglocation.ui.category.CategotryAdminViewModel
import com.inyonghengki.findinglocation.ui.categoryfasilitas.CategotryFasilitaAdminViewModel
import com.inyonghengki.findinglocation.ui.categoryproduct.CategotryProductAdminViewModel
import com.inyonghengki.findinglocation.ui.detail.DetailViewModel
import com.inyonghengki.findinglocation.ui.favorites.FavoriteViewModel
import com.inyonghengki.findinglocation.ui.feedback.FeedbackViewModel
import com.inyonghengki.findinglocation.ui.home.HomeViewModel
import com.inyonghengki.findinglocation.ui.keranjangproduct.KeranjangProductViewModel
import com.inyonghengki.findinglocation.ui.lokasitempat.LokasiTempatViewModel
import com.inyonghengki.findinglocation.ui.menufasilitas.MenuFasilitasViewModel
import com.inyonghengki.findinglocation.ui.menuproduct.MenuProductViewModel
import com.inyonghengki.findinglocation.ui.navigation.NavViewModel
import com.inyonghengki.findinglocation.ui.searchplace.HistorySarchViewModel
import com.inyonghengki.findinglocation.ui.slider.SliderAdminViewModel
import com.inyonghengki.findinglocation.ui.tempat.TempatViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { AuthViewModel(get()) }
    viewModel { TempatViewModel(get()) }
    viewModel { NavViewModel(get()) }
    viewModel { LokasiTempatViewModel(get()) }
    viewModel { MenuProductViewModel(get()) }
    viewModel { MenuFasilitasViewModel(get()) }
    viewModel { CategotryAdminViewModel(get()) }
    viewModel { CategotryProductAdminViewModel(get()) }
    viewModel { CategotryFasilitaAdminViewModel(get()) }
    viewModel { AkunsViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { BaseViewModel(get()) }
    viewModel { SliderAdminViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { FeedbackViewModel(get()) }
    viewModel { KeranjangProductViewModel(get()) }
    viewModel { HistorySarchViewModel(get()) }

}