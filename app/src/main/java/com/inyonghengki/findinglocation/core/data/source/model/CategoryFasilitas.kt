package com.inyonghengki.findinglocation.core.data.source.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryFasilitas(
    val id: Int? = null,
    val parentId: Int? = null,
    val name: String,
    val description: String? = null,
    val image: String? = null,
) : Parcelable