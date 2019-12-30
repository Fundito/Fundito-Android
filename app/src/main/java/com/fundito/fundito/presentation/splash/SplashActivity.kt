package com.fundito.fundito.presentation.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.facebook.AccessToken
import com.fundito.fundito.R
import com.fundito.fundito.common.util.SPUtil
import com.fundito.fundito.common.util.startActivity
import com.fundito.fundito.data.service.NetworkClient
import com.fundito.fundito.presentation.login.LoginActivity
import com.fundito.fundito.presentation.main.MainActivity
import kotlinx.coroutines.launch
import timber.log.Timber


/**
 * Created by mj on 22, December, 2019
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onResume() {
        super.onResume()

        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired

        if(isLoggedIn) {

            lifecycleScope.launch {
                kotlin.runCatching {
                    NetworkClient.userService.signIn(accessToken.token)
                }.onSuccess {
                    Timber.e(it.toString())
                    SPUtil.accessToken = it.token
                    startActivity(MainActivity::class, Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                }.onFailure {
                    Timber.e(it)
                    startActivity(LoginActivity::class, Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            }


        }else {
            startActivity(LoginActivity::class, Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }




    }
}
