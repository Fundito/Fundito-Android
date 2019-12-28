package com.fundito.fundito.presentation.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.fundito.fundito.R
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*


/**
 * Created by mj on 26, December, 2019
 */
class LoginActivity : AppCompatActivity() {

    private var callbackManager: CallbackManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginButton.setOnClickListener(View.OnClickListener {
            //login
            callbackManager = CallbackManager.Factory.create()
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        Log.d("letsSee", "Facebook token: " + loginResult.accessToken.token)
                    }

                    override fun onCancel() {
                        Log.d("letsSee", "Facebook onCancel.")

                    }

                    override fun onError(error: FacebookException) {
                        Log.d("letsSee", "Facebook onError.")

                    }

                })


            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
        })

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }

    var accessToken = AccessToken.getCurrentAccessToken()
    var isLoggedIn = accessToken != null && !accessToken!!.isExpired


}
