package com.inyonghengki.findinglocation.core.data.source.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pengunjung(
    val created_at: String? = null,
    val id: Int? = null,
    val qtyKunjungan: Int? = null,
    val tempatId: Int? = null,
    val tgllahirUser: String? = null,
    val umurUser: Int? = null,
    val updated_at: String? = null,
    val userId: Int?= null,
    var user: User? = null,
    var place: Tempat? = null,
): Parcelable