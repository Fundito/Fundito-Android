@file:Suppress("DEPRECATION")

package com.fundito.fundito.common.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import androidx.annotation.Px
import com.fundito.fundito.R
import com.fundito.fundito.databinding.ViewInvestmentDialBinding
import timber.log.Timber
import kotlin.math.roundToInt


/**
 * Created by mj on 25, December, 2019
 */
class InvestmentDialView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : FrameLayout(context, attributeSet) {

    @Px
    private fun Int.toPixel() = context.resources.displayMetrics.density * this

    /**
     * Custom Listenable, Snappable Horizontal ScrollView
     */
    private val scrollView = ListenableHorizontalScrollView(context).apply {
        overScrollMode = HorizontalScrollView.OVER_SCROLL_NEVER
        isHorizontalScrollBarEnabled = false
    }


    /**
     * LineWidth
     */
    private var bigLineWidth = 4.toPixel()
    private var middleLineWidth = 3.toPixel()
    private var smallLineWidth = 2.toPixel()

    /**
     * LinePaint
     */
    private var bigLinePaint =
        Paint(Paint.ANTI_ALIAS_FLAG).apply { color = resources.getColor(R.color.colorPrimary) }
    private var middleLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.GRAY }
    private var smallLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.LTGRAY }


    /**
     * Line Length
     */
    var bigLineLength: Float = 60.toPixel()
        set(value) {
            invalidate()
            field = value
        }
    var middleLineLength: Float = 30.toPixel()
        set(value) {
            invalidate()
            field = value
        }

    var smallLineLength: Float = 15.toPixel()
        set(value) {
            invalidate()
            field = value
        }

    /**
     * Side Padding For
     */
    private val sidePadding: Int
        get() = resources.displayMetrics.widthPixels / 2 - 50.toPixel().roundToInt()

    /**
     * Custom Child View
     */
    private lateinit var dial: InvestmentDial

    private var currentTextIndex = 0

    /**
     * Custom Properties
     */
    var minPrice = 1000
        set(value) {
            field = value
        }
    var maxPrice = 5000
        set(value) {
            field = value
        }
    val smallLineCount: Int
        get() = (5000 - 1000) / 100 + 1
    val middleLineCount: Int
        get() = (smallLineCount - 1) / 5 + 1

    var contentWidth: Float = 1000.toPixel()

    val smallLineSpacing: Float
        get() {
            return (contentWidth - 100.toPixel()) / (smallLineCount - 1).toFloat()
        }
    val middleLineSpacing: Float
        get() = smallLineSpacing * 5

    init {
        setBackgroundColor(Color.WHITE)

        dial = InvestmentDial(context, null).apply {
            layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)

            setPadding(sidePadding, 0, sidePadding, 0)
        }

        addView(scrollView)
        scrollView.addView(dial)

        scrollView.apply {
            setOnScrollEndListener {

                // it = 0~contentWidth


                //TODO 고쳐야됨
                val index =
                    ((it / (contentWidth - 100.toPixel())) * (smallLineCount)).roundToInt() // 0 ~ smallLineCount
                val pos = (index * smallLineSpacing).roundToInt()


                toScrollWthAnim(pos)
            }
            setOnDidScrollListener {
                //TODO 고쳐야됨

                val newTextIndex = ((it / (contentWidth)) * (middleLineCount)).roundToInt()

                Timber.e(newTextIndex.toString())

                if (currentTextIndex != newTextIndex) {
                    dial.textViews[currentTextIndex].apply {
                        ObjectAnimator.ofArgb(
                            this,
                            "textColor",
                            resources.getColor(R.color.colorPrimary),
                            Color.BLACK
                        ).apply {
                            duration = 500L
                            start()
                        }
                        ValueAnimator.ofInt(20.toPixel().roundToInt(), 0).let { animator ->
                            animator.duration = 300L
                            animator.addUpdateListener {
                                val value = it.animatedValue as Int
                                this.setPadding(0, 0, 0, value)
                            }
                            animator.start()
                        }
                    }
                    dial.textViews[newTextIndex].apply {
                        ObjectAnimator.ofArgb(
                            this,
                            "textColor",
                            Color.BLACK,
                            resources.getColor(R.color.colorPrimary)
                        ).apply {
                            duration = 500L
                            start()
                        }
                        ValueAnimator.ofInt(0, 20.toPixel().roundToInt()).let { animator ->
                            animator.duration = 300L
                            animator.addUpdateListener {
                                val value = it.animatedValue as Int
                                this.setPadding(0, 0, 0, value)
                            }
                            animator.start()
                        }
                    }
//                        .setPadding(0,0,0,20.toPixel().roundToInt())
                    currentTextIndex = newTextIndex
                }
                //distance 0~1
                //padding 0~50
                val distanceFromBeforeTextView = 1
                val distnaceFromNextTextView = 1

//                dial.textViews[index].setPadding(0,0,0,0)
            }

        }
    }


    override fun onDraw(canvas: Canvas) {

        canvas.drawRoundRect(
            width / 2f - bigLineWidth / 2f,
            height - bigLineLength,
            width / 2f + bigLineWidth / 2f,
            height.toFloat(),
            bigLineWidth / 2f,
            bigLineWidth / 2f,
            bigLinePaint
        )

    }


    inner class InvestmentDial @JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null
    ) : FrameLayout(context, attributeSet) {


        private var mBinding: ViewInvestmentDialBinding =
            ViewInvestmentDialBinding.inflate(LayoutInflater.from(context))

        var textViews = listOf(
            mBinding.text1000,
            mBinding.text1500,
            mBinding.text2000,
            mBinding.text2500,
            mBinding.text3000,
            mBinding.text3500,
            mBinding.text4000,
            mBinding.text4500,
            mBinding.text5000
        )

        init {
            addView(
                mBinding.root,
                ViewGroup.LayoutParams(contentWidth.roundToInt(), 150.toPixel().roundToInt())
            )
            setBackgroundColor(Color.TRANSPARENT)
        }


        override fun onDraw(canvas: Canvas) {

            /**
             * 짧은 격자
             */
            repeat(smallLineCount) {
                val centerX = it * smallLineSpacing + paddingLeft + 50.toPixel()
                canvas.drawRoundRect(
                    centerX - smallLineWidth / 2,
                    height - smallLineLength,
                    centerX + smallLineWidth / 2, height.toFloat(),
                    smallLineWidth / 2,
                    smallLineWidth / 2, smallLinePaint
                )
            }


            /**
             * 긴 격자
             */
            repeat(middleLineCount) {
                val centerX = it * middleLineSpacing + paddingLeft + 50.toPixel()
                canvas.drawRoundRect(
                    centerX - middleLineWidth / 2,
                    height - middleLineLength,
                    centerX + middleLineWidth / 2, height.toFloat(),
                    middleLineWidth / 2,
                    middleLineWidth / 2, middleLinePaint
                )
            }


        }
    }
}