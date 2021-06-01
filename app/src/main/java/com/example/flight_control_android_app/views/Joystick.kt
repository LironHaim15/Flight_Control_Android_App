package com.example.flight_control_android_app.views

import com.example.flight_control_android_app.R
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs
import kotlin.math.min

class Joystick @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var headCenter: PointF = PointF()
    private var baseCorner: PointF = PointF()
    private var center: PointF = PointF()
    var onChange: ((Float, Float)->Unit )? = null
    private lateinit var joystickHead :Bitmap
    private lateinit var joystickBase :Bitmap
    private var paint = Paint()
    private var smallerSide :Int = 0
    private var jsHeadDiameter :Int = 0
    private var jsBaseDiameter: Int = 0

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        val jsHead = BitmapFactory.decodeResource(resources, R.drawable.joystick_grey)
        val jsBase = BitmapFactory.decodeResource(resources, R.drawable.joystick_base)
        smallerSide = min(width,height)
        jsHeadDiameter = (smallerSide/4).toInt()
        jsBaseDiameter = (smallerSide/1.5).toInt()
        headCenter = PointF((width/2f)-(jsHeadDiameter/2), (height/2f)-(jsHeadDiameter/2))
        baseCorner = PointF((width/2f)-(jsBaseDiameter/2), (height/2f)-(jsBaseDiameter/2))
        center.x = headCenter.x
        center.y = headCenter.y
        joystickBase = Bitmap.createScaledBitmap(jsBase,jsBaseDiameter,jsBaseDiameter,false)
        joystickHead = Bitmap.createScaledBitmap(jsHead,jsHeadDiameter,jsHeadDiameter,false)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(joystickBase, baseCorner.x ,baseCorner.y ,paint)
        canvas.drawBitmap(joystickHead, headCenter.x, headCenter.y ,paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_MOVE -> touchMove(event.x, event.y)
            MotionEvent.ACTION_UP -> touchUp(event.x, event.y)
        }
        return true
    }

    private fun touchMove(x: Float, y: Float) {
        val jsHeadRadius :Int = jsHeadDiameter/2
        val jsBaseRadius :Int = jsBaseDiameter/2
        val baseCenter = PointF(baseCorner.x + jsBaseRadius, baseCorner.y + jsBaseRadius)
        if ((abs(x - (baseCenter.x)) <= jsBaseRadius) && (abs(y - baseCenter.y) <= jsBaseRadius)) {
            headCenter.x = x-jsHeadRadius
            headCenter.y = y-jsHeadRadius

            onChange?.invoke(((x - baseCenter.x) / (jsBaseRadius)), (y - baseCenter.y) / (jsBaseRadius))
        }
        invalidate()
    }

    private fun touchUp(x: Float, y: Float) {
        headCenter.x = center.x
        headCenter.y = center.y
        invalidate()
    }
}


