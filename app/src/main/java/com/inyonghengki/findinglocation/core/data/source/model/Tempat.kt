package com.inyonghengki.findinglocation.core.data.source.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tempat(

    val id: Int? = null,
    val userId: Int? = null,
    val alamatId: Int? = null,
    val kategoriId: Int? = null,
    val imageTempat: String? = null,
    val imagaPemilik: String? = null,
    val nameTempat: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val kota:String? = null,
    val alamat:String? = null,
    val openH: String? = null,
    val closeH:String? = null,
    val kategori:String? = null,
    val isActive: Boolean? = null,
    val status:String? = null,
    val deskription: String? = null,
    val pengunjung: Int? = null,
    val avgReview: Float? = null,
    val rating: Double = 5.0,
    val updated_at: String? = null,
    val created_at:String? = null,
    val category: Category? = null,
    val address: LokasiTempat? = null,
    var user: User? = null,

    ) : Parcelable
