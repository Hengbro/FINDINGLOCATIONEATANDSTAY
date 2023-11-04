package com.inyonghengki.findinglocation.core.data.source.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class KeranjangProduct(

    val id: Int? = null,
    val userId: Int? = null,
    val tempatId: Int? = null,
    val productId: Int? = null,
    val cartPlaceId: Int? = null,
    val isActive: Boolean? = null,
    val isOrder: Boolean? = null,
    val note: String? = null,
    val qty: String? = null,
    val tot_harga: String? = null,
    val updated_at: String? = null,
    val created_at: String? = null,
    val user: User? = null,
    val place: Tempat? = null,
    val product: MenuProduct? = null,

):Parcelable