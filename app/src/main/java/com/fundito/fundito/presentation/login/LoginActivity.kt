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
import com.fundito.fundito.common.showAlert
import com.fundito.fundito.common.util.AuthUtil
import com.fundito.fundito.common.util.SPUtil
import com.fundito.fundito.common.util.startActivity
import com.fundito.fundito.common.widget.hideLoading
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import com.fundito.fundito.common.widget.showLoading
import com.fundito.fundito.data.service.NetworkClient
import com.fundito.fundito.presentation.main.MainActivity
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.HttpException
import kotlin.coroutines.resume


/**
 * Created by mj on 26, December, 2019
 */
class LoginActivity : AppCompatActivity() {

    private val callbackManager = CallbackManager.Factory.create()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        facebookLoginButton.apply {
            setPermissions("public_profile","email")

            registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {
                    lifecycleScope.launch {

                        val fcmToken = suspendCancellableCoroutine<String> {continuation->
                            FirebaseInstanceId.getInstance().instanceId
                                .addOnSuccessListener {
                                    continuation.resume(it.token)
                                }.addOnFailureListener {
                                    continuation.cancel(it)
                                }
                        }

                        showLoading()
                        kotlin.runCatching {
                          //  NetworkClient.userService.signIn(AccessToken.getCurrentAccessToken().token ?: "EAAGhMO97yxABAHZBg9Pn6DCoAwhsZCEQrZCGZAGNNYCdxRqCjZAkaD1SiI6CSJVTdtF9U8N1Nm9qfnksZApdH5ORCNiwPAM93FuZCUuFVspF2cZB8knk0OyZAskuFWqZBorom1TCCVnvF4xjRR9ownDIFFUFSFbzD4HTvUuMYFfaNYoWDbZCHWLt9gJ2YtioxCiNZCgcWqMCfHFj8NqVh24UG1U2OQ2ubFdmTZCL612nyZBdSOuEJ58pKhT9eA")
                            NetworkClient.userService.signIn(
                                AccessToken.getCurrentAccessToken().token ?: "",
                                fcmToken
                            )
                        }.onSuccess {
                            SPUtil.accessToken = it.token
                            val intent = Intent(this@LoginActivity, LoginNicknameActivity::class.java)
                            startActivity(intent)
                        }.onFailure {
                            if((it as? HttpException)?.code() == 401) {
                                startActivity(LoginNicknameActivity::class)
                            }else {
                                AuthUtil.logout()
                                showAlert("로그인 실패")
                            }
                        }
                        hideLoading()
                    }
                }

                override fun onCancel() {
                    showAlert("페이스북 로그인 취소")
                }

                override fun onError(error: FacebookException?) {
                    showAlert(error?.localizedMessage ?: "페이스북 로그인 실패")
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