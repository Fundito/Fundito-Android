package com.fundito.fundito.presentation.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.fundito.fundito.R
import com.fundito.fundito.common.showAlert
import com.fundito.fundito.common.util.AuthUtil
import com.fundito.fundito.common.util.SPUtil
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
import timber.log.Timber
import kotlin.coroutines.resume


/**
 * Created by mj on 26, December, 2019
 */
class LoginActivity : AppCompatActivity() {

    private val callbackManager = CallbackManager.Factory.create()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        window.statusBarColor = Color.parseColor("#f9f8f8")

        pager.apply {
            adapter = LoginGuidePagerAdapter().apply {
                submitItems(listOf(
                    Triple("먹어보고","내 입맛에 맞는지 직접 판단하세요.",R.drawable.guide1),
                    Triple("펀딩하고","가게가 목표매출에 도달할 수\n있도록 자유롭게 홍보하세요.",R.drawable.guide3),
                    Triple("돌려받자 !","초기 투자로 최대 200%까지\n돌려 받으세요.",R.drawable.guide2)
                ))
            }

            registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    for(v in pageIndicatorContainer.children) {
                        v.isActivated = false
                    }
                    pageIndicatorContainer.getChildAt(position).isActivated = true
                }
            })
        }

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
                            NetworkClient.userService.signIn(
                                AccessToken.getCurrentAccessToken().token ?: "",
                                fcmToken
                            )
                        }.onSuccess {
                            SPUtil.accessToken = it.token

                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)

                        }.onFailure {
                            if((it as? HttpException)?.code() == 401) {
                                val userName = it.message?.dropWhile { it != '[' }?.dropLastWhile { it != ']' }?.filter { it != '[' && it != ']' } ?: ""
                                Timber.e("유저 이름 : $userName")
                                startActivity(LoginNicknameActivity.newIntent(this@LoginActivity,userName))
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