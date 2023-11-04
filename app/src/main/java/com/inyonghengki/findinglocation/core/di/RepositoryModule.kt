package com.inyonghengki.findinglocation.core.di

import com.inyonghengki.findinglocation.core.data.repository.AppRepository
import com.inyonghengki.findinglocation.core.data.repository.CategoryAdminRepository
import com.inyonghengki.findinglocation.core.data.repository.CategoryFasilitasAdminRepository
import com.inyonghengki.findinglocation.core.data.repository.CategoryProductAdminRepository
import com.inyonghengki.findinglocation.core.data.repository.FavoriteRepository
import com.inyonghengki.findinglocation.core.data.repository.HistorySearchRepository
import com.inyonghengki.findinglocation.core.data.repository.KeranjangProductRepository
import com.inyonghengki.findinglocation.core.data.repository.KeranjangTempatRepository
import com.inyonghengki.findinglocation.core.data.repository.LokasiRepository
import com.inyonghengki.findinglocation.core.data.repository.MenuFasilitasRepository
import com.inyonghengki.findinglocation.core.data.repository.MenuProductRepository
import com.inyonghengki.findinglocation.core.data.repository.SliderAdminRepository
import com.inyonghengki.findinglocation.core.data.repository.UlasanRepository
import org.koin.dsl.module

val repositoryModule = module {

    single { AppRepository(get(), get()) }
    single { LokasiRepository(get(), get()) }
    single { MenuProductRepository(get(), get()) }
    single { MenuFasilitasRepository(get(), get()) }
    single { CategoryAdminRepository(get(), get()) }
    single { CategoryProductAdminRepository(get(), get()) }
    single { CategoryFasilitasAdminRepository(get(), get()) }
    single { SliderAdminRepository(get(), get()) }
    single { UlasanRepository(get(), get()) }
    single { FavoriteRepository(get(), get()) }
    single { KeranjangTempatRepository(get(), get()) }
    single { KeranjangProductRepository(get(), get()) }
    single { HistorySearchRepository(get(), get()) }

}