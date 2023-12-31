package com.inyonghengki.findinglocation.util

import com.chibatching.kotpref.KotprefModel
import com.inyonghengki.findinglocation.core.data.source.model.User
import com.inyongtisto.myhelper.extension.toJson
import com.inyongtisto.myhelper.extension.toModel

object Pref : KotprefModel() {

    var isLogin by booleanPref(false)
    var user by stringPref()
    var token by stringPref("token")

    fun setUser(data: User?) {
        user = data.toJson()
    }

    fun getUser(): User? {
        if(user.isEmpty()) return null
        return user.toModel(User::class.java)
    }
}

fun getTempatId() = Pref.getUser()?.tempat?.id