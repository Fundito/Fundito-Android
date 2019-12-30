package com.fundito.fundito.common

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.fundito.fundito.R

/**
 * Created by mj on 24, December, 2019
 */

@BindingAdapter("srcUrl","placeholder",requireAll = false)
fun ImageView.loadUrlAsync(url : String?, placeholder : Drawable? = null) {
    if(url == null) {
        Glide.with(this).load(placeholder ?: R.drawable.logo_img).error(R.drawable.logo_img).into(this)
    }else {
        Glide.with(this).load(url)
            .apply {
                if(placeholder != null)
                    placeholder(placeholder)
            }
            .error(R.drawable.logo_img)
            .into(this)
    }
}

@BindingAdapter("android:visibility")
fun View.setVisibilityBinding(isVisible : Boolean) {
    this.isVisible = isVisible
}

@BindingAdapter("app:selected_binding")
fun View.setSelectedBinding(isSelected : Boolean) {
    this.isSelected = isSelected
}

@BindingAdapter("app:activated")
fun View.setActivateBinding(isActivated : Boolean) {
    this.isActivated = isActivated
}