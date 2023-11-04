package com.inyonghengki.findinglocation.core.data.source.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MenuFasilitas(
    val category: String? = null,
    val categoryId: Int? = null,
    val created_at: String? = null,
    val description: String? = null,
    val id: Int? = null,
    val image: String? = null,
    val isActive: Boolean? = null,
    val name: String? = null,
    val stock: Int? = null,
    val tempatId: Int? = null,
    val updated_at: String? = null,
    var place: Tempat? = null,
) : Parcelable