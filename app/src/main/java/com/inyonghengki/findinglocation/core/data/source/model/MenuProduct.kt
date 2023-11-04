package com.inyonghengki.findinglocation.core.data.source.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MenuProduct(
    val id: Int?= null,
    val tempatId: Int?= null,
    val name: String?= null,
    val image: String?= null,
    val price: Int?= null,
    val category: String?= null,
    val categoryId: Int? = null,
    val description: String?= null,
    val created_at: String?= null,
    val isActive: Boolean? = null,
    val updated_at: String?= null,

) : Parcelable