package com.example.yourbank.ui.customView

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.yourbank.ui.customView.interfacesCustomView.InterfaceAnimationLoadingCard
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class AnimationLoadingCard @JvmOverloads constructor(context : Context, attrs : AttributeSet? = null, style: Int = 0) : View(context,attrs,style) , InterfaceAnimationLoadingCard {

    private val widthProgressBar = 10
    private val radiusProgressBar = 40

    private var canvas = Canvas()
    private var isStart = false

    private val animProgressBar = ValueAnimatorX.ofValue(0f, (2*PI).toFloat()).apply {
        vectorFunction {
            if (currentX >= 2*PI) currentX = 0f
            10f
        }
        render { value -> drawLoadingProgressBar(canvas,value)  }
    }

    private val paint = Paint().apply { isAntiAlias = true }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        this.canvas = canvas

        if(isStart) {
            animProgressBar.render()
        }
    }

    private fun drawLoadingProgressBar(canvas: Canvas, radian: Float) {
        var angle = radian
        var alpha = 255f
        val c = (angle - (radian - 2f))/0.01f

        while (angle > radian - 2f) {
            angle -= 0.01f
            alpha -= 255f/c
            paint.color = Color.argb(alpha.toInt(),0,255,250)

            val x = (convertDpToPixels(radiusProgressBar)) * cos(angle)
            val y = (convertDpToPixels(radiusProgressBar)) * sin(angle)

            val x1 = (convertDpToPixels(radiusProgressBar) - convertDpToPixels(widthProgressBar)) * cos(angle)
            val y1 = (convertDpToPixels(radiusProgressBar) - convertDpToPixels(widthProgressBar)) * sin(angle)

            canvas.drawLine(width/2f + x1,height/2f - y1, (width/2f + x), (height/2f - y),paint)

            paint.color = Color.argb(alpha.toInt(),255,0,250)

            canvas.drawLine(width/2f - x1,height/2f + y1, (width/2f - x), (height/2f + y),paint)

            invalidate()
        }
    }

    private fun convertDpToPixels(dp: Int) = (dp * context.resources.displayMetrics.density).toInt()

    override fun start() {
        isStart = true
        invalidate()
    }

    override fun stop() {
        animProgressBar.speed = 0f
        isStart = false
    }
}