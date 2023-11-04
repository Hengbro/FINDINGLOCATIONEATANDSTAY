package com.inyonghengki.findinglocation.core.data.source.model

import android.os.Parcelable
import com.inyonghengki.findinglocation.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class Slider(
    val id: Int? = null,
    val name: String? = null,
    val description: String? = null,
    val image: String? = null,
    val imageDummy: Int = R.drawable.assetdua,
):Parcelable