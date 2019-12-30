package com.fundito.fundito.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.fundito.fundito.R
import com.fundito.fundito.common.util.SPUtil
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import com.fundito.fundito.data.service.NetworkClient
import com.fundito.fundito.presentation.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.launch
import timber.log.Timber


/**
 * Created by mj on 26, December, 2019
 */
class LoginActivity : AppCompatActivity() {

    private val callbackManager = CallbackManager.Factory.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        facebookLoginButton.apply {
            setPermissions("public_profile","email","user_friends")

            registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {
                    lifecycleScope.launch {

                        kotlin.runCatching {
                            NetworkClient.userService.signIn(AccessToken.getCurrentAccessToken().token ?: "")
                        }.onSuccess {
                            SPUtil.accessToken = it.token
                            Timber.e( "success")
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                        }.onFailure {
                            Timber.e( "fail")
                            Timber.e(it.message.toString())
                        }
                    }
                }

                override fun onCancel() {
                }

                override fun onError(error: FacebookException?) {
                }
            })
        }

        loginButton setOnDebounceClickListener {
            facebookLoginButton.performClick()
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }


}