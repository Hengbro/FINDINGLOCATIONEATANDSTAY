package com.inyonghengki.findinglocation.util

 fun String?.defaultError(): String{
    return this?: Constants.DEFAULT_ERROR
 }
 fun String.toUrlProduct(): String{
     return Constants.BASE_URL + "storage/menuproduct/" + this
 }

fun String.toUrlFasilitas(): String{
    return Constants.BASE_URL + "storage/menufasilitas/"+ this
}

fun String.toUrlTempat(): String{
    return Constants.BASE_URL + "storage/imagetempat/"+ this
}

fun String.toUrlPemilik(): String{
    return Constants.BASE_URL + "storage/imagepemilik/"+ this
}

fun String?.toUrlCategory(): String{
    return Constants.BASE_URL + "storage/category/"+ this
}

fun String?.toUrlCategoryPro(): String{
    return Constants.BASE_URL + "storage/categoryproduct/"+ this
}

fun String?.toUrlCategoryFas(): String{
    return Constants.BASE_URL + "storage/categoryfasilitas/"+ this
}

fun String?.toUrlSlider(): String{
    return Constants.BASE_URL + "storage/slider/"+ this
}
