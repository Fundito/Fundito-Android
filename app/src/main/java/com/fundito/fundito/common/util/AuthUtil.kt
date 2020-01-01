package com.fundito.fundito.common.util

import com.facebook.login.LoginManager

/**
 * Created by mj on 01, January, 2020
 */
object AuthUtil {
    fun logout() {
        LoginManager.getInstance().logOut()
        SPUtil.accessToken = ""
    }
}