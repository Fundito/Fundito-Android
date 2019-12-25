package com.fundito.fundito.common.util

import android.net.Uri
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

/**
 * Created by mj on 26, December, 2019
 */
object MultipartUtil {

    fun stringToPart(text: String) : RequestBody {
        return text.toRequestBody(MultipartBody.FORM)
    }

    fun uriToPart(name: String, uri: Uri): MultipartBody.Part {

        val file = File(try {
            UriUtil.getPathFromUri(uri)!!
        } catch (t: Throwable) {
            uri.path!!
        })

        return MultipartBody.Part.createFormData(
            name,
            file.name,
            file.asRequestBody()
        )
    }

}
