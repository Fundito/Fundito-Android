package com.fundito.fundito.presentation.store

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.share.Sharer
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog
import com.fundito.fundito.R
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import kotlinx.android.synthetic.main.activity_store_cheer.*


/**
 * Created by mj on 26, December, 2019
 */
class StoreCheerActivity : AppCompatActivity() {

    companion object {
        private const val ARG_STORE_NAME = "ARG_STORE_NAME"

        fun newIntent(context: Context, storeName: String) : Intent {
            return Intent(context,StoreCheerActivity::class.java).apply {
                putExtra(ARG_STORE_NAME,storeName)
            }
        }
    }

    private val storeName: String
    get() = intent?.getStringExtra(ARG_STORE_NAME) ?: ""


    private lateinit var shareDialog :ShareDialog
    private val callbackManager = CallbackManager.Factory.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_cheer)

        shopName.text = storeName

        shareDialog = ShareDialog(this)
        shareDialog.registerCallback(callbackManager,object : FacebookCallback<Sharer.Result> {
            override fun onSuccess(result: Sharer.Result?) {
            }

            override fun onCancel() {
            }

            override fun onError(error: FacebookException?) {
            }
        })


        cancelButton setOnDebounceClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        cheerButton setOnDebounceClickListener {
            val content = ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://fundito.page.link/XktS"))
                .setQuote("$storeName 에 투자하세요! \n#Fundito")
                .build()
            shareDialog.show(content,ShareDialog.Mode.AUTOMATIC)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode,resultCode,data)
    }
    
}