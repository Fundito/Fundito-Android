package com.fundito.fundito.common.view

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.HorizontalScrollView
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart

/**
 * Created by mj on 25, December, 2019
 */
typealias ListenableScrollListener = (Int) -> Unit

class ListenableHorizontalScrollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : HorizontalScrollView(context, attrs) {

    private var userTouched = false
    private val canEmitEvent: Boolean
        get() = !userTouched

    private var isAnimating = false

    private var onScrollEnd: ListenableScrollListener? = null
    private var onDidScroll: ListenableScrollListener? = null

    private var currentAnimator : Animator? = null


    fun setOnScrollEndListener(listener: ListenableScrollListener) {
        this.onScrollEnd = listener
    }
    fun setOnDidScrollListener(listener : ListenableScrollListener) {
        this.onDidScroll = listener
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        onDidScroll?.invoke(l)

        if (canEmitEvent) {
            onScrollEnd?.invoke(l)
        }

        super.onScrollChanged(l, t, oldl, oldt)
    }


    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        currentAnimator?.cancel()
        when (ev?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                userTouched = true
            }
            MotionEvent.ACTION_UP -> {
                onScrollEnd?.invoke(this.scrollX)
                userTouched = false
            }

        }
        return super.onTouchEvent(ev)
    }

    fun toScrollWthAnim(scrollX: Int, duration: Long = 200L) {
        if (isAnimating) return

        currentAnimator = ObjectAnimator.ofInt(this, "scrollX", scrollX).apply {
            startDelay = 150L
            setAutoCancel(true)
            this.duration = duration

            doOnStart { isAnimating = true }
            doOnEnd { isAnimating = false }
            start()
        }
    }

}