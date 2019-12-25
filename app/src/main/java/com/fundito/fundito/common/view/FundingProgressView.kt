package com.fundito.fundito.common.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Px
import kotlin.math.roundToInt

/**
 * Created by mj on 25, December, 2019
 */
class FundingProgressView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    View(context, attrs) {

    @Px
    private fun toPixel(dp: Int): Float {
        return context.resources.displayMetrics.density * dp
    }

    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val outlineCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    var lineWidth = 0.5f
        set(value) {
            invalidate()
            field = value
        }

    var circleRadius: Float = toPixel(3)
        set(value) {
            invalidate()
            field = value
        }

    @ColorInt
    var activeCircleColor: Int = Color.BLUE
        set(value) {
            invalidate()
            field = value
        }

    @ColorInt
    var inActiveCircleColor: Int = Color.RED
        set(value) {
            invalidate()
            field = value
        }

    var max: Int = 100
    var circleCount = 25
    var progress: Int = 83


    override fun onDraw(canvas: Canvas) {

        outlineCirclePaint.strokeWidth = 2f
        outlineCirclePaint.color = activeCircleColor

        /**
         * 가로 선을 그린다
         */
        linePaint.color = Color.BLUE
        linePaint.strokeWidth = lineWidth
        canvas.drawLine(0f, height / 2f, width.toFloat(), height / 2f, linePaint)


        /**
         * 동그라미를 그린다
         */
        for (i in 0 until circleCount) {
            val centerX = circleRadius + i * width / circleCount

            val isActive = isActiveCircleIndex(i)

            circlePaint.color = if(isActive) activeCircleColor else inActiveCircleColor

            canvas.drawCircle(centerX, height / 2f, circleRadius, circlePaint)

            if(isActive && (i + 1 == circleCount || !isActiveCircleIndex(i + 1))) {
                canvas.drawCircle(centerX, height/2f,circleRadius * 1.3f, circlePaint)
                canvas.drawCircle(centerX,height/2f,circleRadius *1.7f,outlineCirclePaint)


            }



        }


    }

    private fun isActiveCircleIndex(circleIndex : Int) : Boolean {
        val curProgress = (progress/max.toFloat() * circleCount).roundToInt()
        return circleIndex < curProgress
    }

}