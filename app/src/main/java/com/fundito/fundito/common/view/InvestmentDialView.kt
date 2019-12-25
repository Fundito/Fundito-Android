@file:Suppress("DEPRECATION")

package com.fundito.fundito.common.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import androidx.annotation.Px
import com.fundito.fundito.R
import com.fundito.fundito.databinding.ViewInvestmentDialBinding
import kotlin.math.roundToInt


/**
 * Created by mj on 25, December, 2019
 */
typealias OnInvestmentValueChangedListener = (Int) -> Unit
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
    private var middleLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.GRAY }
    private var smallLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.LTGRAY }

    /**
     * Side Padding For
     */
    private val sidePadding: Int
        get() = resources.displayMetrics.widthPixels / 2 - 50.toPixel().roundToInt()

    /**
     * Custom Child View
     */
    private var dial: InvestmentDial

    private var currentTextIndex = 0

    private var currentGridIndex = 0


    /**
     * Line Length
     */
    private val bigLineLength: Float = 60.toPixel()
    private val middleLineLength: Float = 30.toPixel()
    private val smallLineLength: Float = 15.toPixel()

    var onInvestmentValueChangedListener : OnInvestmentValueChangedListener? = null


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
            /**
             * Scroll End
             */
            setOnScrollEndListener {
                val newGridIndex = ((it / (contentWidth - 100.toPixel())) * (smallLineCount - 1)).roundToInt()

                if(currentGridIndex != newGridIndex) {
                    val scrollPos = (newGridIndex * smallLineSpacing).roundToInt()
                    toScrollWthAnim(scrollPos)
                    currentGridIndex = newGridIndex
                }
            }
            /**
             * When Any Scroll Did
             */
            setOnDidScrollListener {

                /**
                 * invoke Listener for emit current Value
                 */
                val newGridIndex = ((it / (contentWidth - 100.toPixel())) * (smallLineCount - 1)).roundToInt()
                onInvestmentValueChangedListener?.invoke(newGridIndex * 100 + minPrice)

                /**
                 * Animation
                 */
                val newTextIndex = ((it / (contentWidth)) * (middleLineCount)).roundToInt()

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
                        ValueAnimator.ofFloat(1f,0f).let {animator->
                            animator.duration = 300L
                            animator.addUpdateListener {
                                val value = it.animatedValue as Float

                                this.setPadding(0,0,0,(value * 20).roundToInt())
                                this.textSize = 18 + value * 6
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
                        ValueAnimator.ofFloat(0f,1f).let {animator->
                            animator.duration = 300L
                            animator.addUpdateListener {
                                val value = it.animatedValue as Float

                                this.setPadding(0,0,0,(value * 20).roundToInt())
                                this.textSize = 18 + value * 6
                            }
                            animator.start()
                        }
                    }

                    currentTextIndex = newTextIndex
                }
            }
        }

        View(context).apply {
            val radius = bigLineWidth/2
            background = ShapeDrawable(RoundRectShape(floatArrayOf(radius,radius,radius,radius,radius,radius,radius,radius),null,null))
            backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))

            layoutParams = LayoutParams(bigLineWidth.roundToInt(),bigLineLength.roundToInt()).apply {
                gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
            }
            addView(this)
        }
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