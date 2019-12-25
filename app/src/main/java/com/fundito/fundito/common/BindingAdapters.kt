package com.fundito.fundito.common

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * Created by mj on 24, December, 2019
 */

@BindingAdapter("srcUrl","placeholder",requireAll = false)
fun ImageView.loadUrlAsync(url : String?, placeholder : Drawable? = null) {
    if(url == null) {
        Glide.with(this).load(placeholder).into(this)
    }else {
        Glide.with(this).load(url)
            .apply {
                if(placeholder != null)
                    placeholder(placeholder)
            }
            .into(this)
    }
}