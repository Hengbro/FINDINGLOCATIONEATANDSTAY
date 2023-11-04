package com.inyonghengki.findinglocation.core.data.source.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LokasiTempat(
    val alamat: String? = null,
    val created_at: String? = null,
    val email: String? = null,
    val id: Int? = null,
    val isActive: Boolean? = null,
    val kecamatan: String? = null,
    val kecamatanId: Int? = null,
    val kodepos: String? = null,
    val kota: String? = null,
    val kotaId: Int? = null,
    val label: String? = null,
    val phone: String? = null,
    val provinsi: String? = null,
    val provinsiId: Int? = null,
    val tempatId: Int? = null,
    val updated_at: String? = null
) : Parcelable