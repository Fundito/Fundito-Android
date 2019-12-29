package com.fundito.fundito.common.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.withClip
import com.fundito.fundito.R
import com.fundito.fundito.common.util.toPixel

/**
 * Created by mj on 26, December, 2019
 */
class FundingGraphView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    var progress: Float = 1.0f
    set(value) {

        progressForAnimation = value
        field = value
        startAnimation()
    }
    var progressForAnimation: Float = 1.0f

    fun startAnimation() {

        ObjectAnimator.ofFloat(this,"progressForAnimation",1f,progress).apply {
            duration = 1500L
            addUpdateListener {
                invalidate()
            }
            start()
        }
    }

    private val outline = Path()

    private var progressColor = Color.parseColor("#aaaec6")
    private val backgroundColor = Color.parseColor("#cccccc")

    private val outlinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = resources.getColor(R.color.colorPrimary)
        strokeWidth = 1.toPixel()
        style = Paint.Style.STROKE
    }
    private val linePadding = 1.toPixel()

    private val gradientPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        shader = LinearGradient(0f, 0f, 0f, 115.toPixel(), backgroundColor, Color.WHITE, Shader.TileMode.MIRROR)
    }

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        shader = LinearGradient(0f, 0f, 0f, 115.toPixel(), progressColor, Color.WHITE, Shader.TileMode.MIRROR)
    }

    override fun onDraw(canvas: Canvas) {

        outline.reset()
        outline.moveTo(0f, 0f)
        outline.lineTo(width - 30.toPixel(), 0f)
        outline.lineTo(width.toFloat(), 30.toPixel())
        outline.lineTo(width.toFloat(), height.toFloat())
        outline.lineTo(0f, height.toFloat())

        outline.close()

        canvas.withClip(outline) {
            canvas.drawPaint(gradientPaint)

            canvas.drawRect(0f, height * (1 - progressForAnimation), width.toFloat(), height.toFloat(), progressPaint)
        }

        canvas.drawLine(0f,linePadding,width-30.toPixel(),linePadding,outlinePaint)
        canvas.drawLine(width-30.toPixel(),linePadding,width.toFloat() - linePadding,30.toPixel(),outlinePaint)
        canvas.drawLine(width.toFloat() - linePadding,30.toPixel(),width.toFloat() - linePadding,height.toPixel(),outlinePaint)

    }
}