package com.inyonghengki.findinglocation.core.data.source.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class KeranjangTempat(
    val id: Int? = null,
    val tempatId: Int? = null,
    val productId: Int? = null,
    val userId: Int? = null,
    val isActive: Boolean? = null,
    val sum_harga: Int? = null,
    val sum_qty: Int? = null,
    val lastInsert: String? = null,
    val status: String? = null,
    val created_at: String? = null,
    val updated_at: String? = null,
    var user: User? = null,
    var place: Tempat? = null,
): Parcelable