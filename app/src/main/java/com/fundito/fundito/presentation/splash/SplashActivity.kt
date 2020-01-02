package com.fundito.fundito.presentation.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.facebook.AccessToken
import com.fundito.fundito.R
import com.fundito.fundito.common.util.AuthUtil
import com.fundito.fundito.common.util.SPUtil
import com.fundito.fundito.common.util.startActivity
import com.fundito.fundito.data.service.NetworkClient
import com.fundito.fundito.presentation.login.LoginActivity
import com.fundito.fundito.presentation.main.MainActivity
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import kotlin.coroutines.resume


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

                    val fcmToken = suspendCancellableCoroutine<String> {continuation->
                        FirebaseInstanceId.getInstance().instanceId
                            .addOnSuccessListener {
                                continuation.resume(it.token)
                            }.addOnFailureListener {
                                continuation.cancel(it)
                            }
                    }

                    NetworkClient.userService.signIn(accessToken.token,fcmToken)
                }.onSuccess {
                    Timber.e(it.toString())
                    SPUtil.accessToken = it.token
                    startActivity(MainActivity::class, Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                }.onFailure {
                    Timber.e(it)
                    AuthUtil.logout()
                    startActivity(LoginActivity::class, Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            }

        }else {
            startActivity(LoginActivity::class, Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }




    }
}
