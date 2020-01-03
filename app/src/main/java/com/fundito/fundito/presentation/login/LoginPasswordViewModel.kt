package com.fundito.fundito.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.AccessToken
import com.fundito.fundito.common.util.SPUtil
import com.fundito.fundito.common.widget.KeyboardDialogFragment
import com.fundito.fundito.common.widget.Once
import com.fundito.fundito.data.service.CardCreateRequest
import com.fundito.fundito.data.service.NetworkClient
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume

/**
 * Created by mj on 01, January, 2020
 */
class LoginPasswordViewModel @Inject constructor() : ViewModel() {

    private val _password : MutableLiveData<String> = MutableLiveData("")
    val password : LiveData<String> = _password

    private val _passwordCheck : MutableLiveData<String> = MutableLiveData("")
    val passwordCheck : LiveData<String> = _passwordCheck




    private val _phase : MutableLiveData<Int> = MutableLiveData(0)
    val phase : LiveData<Int> = _phase

    private val _passwordMatched : MutableLiveData<Once<Boolean>> = MutableLiveData()
    val passwordMatched : LiveData<Once<Boolean>> = _passwordMatched
    
    private val _shakeAnim : MutableLiveData<Once<Unit>> = MutableLiveData()
    val shakeAnim : LiveData<Once<Unit>> = _shakeAnim

    private val _signUpResult : MutableLiveData<Once<Boolean>> = MutableLiveData()
    val signUpResult : LiveData<Once<Boolean>> = _signUpResult

    var name = ""
    var nickName = ""
    lateinit var card: CardCreateRequest

    fun onTypedPassword(num : Int) {

        val password = password.value ?: return

        if(num != -1) {

            _password.value = password + num.toString()
        }else {
            if(password.length == 1)
                _shakeAnim.value = Once(Unit)
            _password.value = password.dropLast(1)

        }

        if(this.password.value?.length == KeyboardDialogFragment.PASSWORD_MAX_LEN) {
            _phase.value = 1
        }

    }

    fun onTypedPasswordCheck(num : Int) {

        val passwordCheck = passwordCheck.value ?: return

        if(num != -1) {
            _passwordCheck.value = passwordCheck + num.toString()
        }else {
            if(passwordCheck.length == 1)
                _shakeAnim.value = Once(Unit)
            _passwordCheck.value = passwordCheck.dropLast(1)
        }

        if(this.passwordCheck.value?.length == KeyboardDialogFragment.PASSWORD_MAX_LEN) {

            val success = password.value == this.passwordCheck.value

            _passwordMatched.value = Once(success)

            if(success) {
                viewModelScope.launch {
                    kotlin.runCatching {
                        val accessToken  = AccessToken.getCurrentAccessToken().token
                        val fcmToken = suspendCancellableCoroutine<String> {continuation->
                            FirebaseInstanceId.getInstance().instanceId
                                .addOnSuccessListener {
                                    continuation.resume(it.token)
                                }.addOnFailureListener {
                                    continuation.cancel(it)
                                }
                        }


                        val token = NetworkClient.userService.signUp(accessToken,this@LoginPasswordViewModel.nickName,this@LoginPasswordViewModel.password.value!!)
                        SPUtil.accessToken = token.token
                        NetworkClient.cardService.createCard(card)
                        NetworkClient.userService.signIn(accessToken,fcmToken)

                    }.onSuccess {

                        _signUpResult.value = Once(true)
                    }.onFailure {
                        Timber.e(it)
                        _signUpResult.value = Once(false)
                    }
                }
            }
        }
    }


}