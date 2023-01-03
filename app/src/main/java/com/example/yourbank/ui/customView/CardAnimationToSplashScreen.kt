package com.example.yourbank.ui.customView

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.math.PI
import kotlin.math.sin

@SuppressLint("CustomSplashScreen")
class CardAnimationToSplashScreen @JvmOverloads constructor(context : Context, attrs : AttributeSet? = null, style: Int = 0) : View(context,attrs,style) {

    private val listSquares = ArrayList<Square>()
    data class Square(val side: Float, var color: Int, val x: Float, var y: Float, val i: Int, val j :Int, val immutableX :Float, val immutableY : Float)

    private val paintSquare = Paint().apply { isAntiAlias = true }

    private val paintStrokeSquare = Paint().apply {
        color = Color.rgb(150, 150, 150)
        setStyle(Paint.Style.STROKE)
        strokeWidth = 2f
    }

    private val padding = 5f
    private val nx = 7f
    private val ny = 3f

    private val colorYellow =  Color.rgb(255, 222, 0)
    private val colorYellowLight =  Color.rgb(255, 234, 88)
    private val colorYellowExtraLight =  Color.rgb(255, 249, 201)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        createListSquares(width,height)
    }

    private fun createListSquares(width: Int, height : Int) {

        val color = colorYellow
        val side  = ((width - (nx+1) * padding)/ nx)

        for (i in 0..((nx-1).toInt())) {
            for (j in 0..((ny-1).toInt())) {

                val x = i * side + (i + 1) * padding
                val y = j * side + (j + 1) * padding + (height - ny*side - ny*padding) - padding

                listSquares.add(Square(side,color,x,y,i,j,x,y))
            }
        }
    }

    private val animSquare = ValueAnimatorX.ofValue(0f, nx).apply {
        vectorFunction { x ->
            if(x.toInt() == x2.toInt()) {currentX = 0f}
            10f
        }
        render { value -> mathSquare(value) }
    }

    private lateinit var canvas : Canvas

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        this.canvas = canvas

        animSquare.render()
    }

    private fun mathSquare(value: Float) {
        listSquares.forEach { square ->

            if(square.i == value.toInt() - 1 || square.i == value.toInt() + 1) {
                square.y = square.immutableY - convertDpToPixels(5)
                square.color = colorYellowLight
            }

            if(square.i == value.toInt()) {
                square.y = square.immutableY - convertDpToPixels(10)
                square.color = colorYellowExtraLight
            }

            if(square.i < value.toInt() - 1 || square.i > value.toInt() + 1) {
                square.y = square.immutableY
                square.color = colorYellow
            }
        }
        listSquares.forEach { square -> drawSquare(canvas,square) }
        invalidate()
    }

    private fun drawSquare(canvas: Canvas,square: Square) {
        val side = square.side

        val rect = RectF(square.x, square.y,square.x + side ,square.y + side)
        paintSquare.color = square.color

        canvas.drawRect(rect,paintSquare)
        canvas.drawRect(rect,paintStrokeSquare)
    }

    private fun convertDpToPixels(dp: Int) = (dp * context.resources.displayMetrics.density).toInt()
}