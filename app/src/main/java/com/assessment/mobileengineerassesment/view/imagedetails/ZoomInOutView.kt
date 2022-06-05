package com.assessment.mobileengineerassesment.view.imagedetails

import android.annotation.SuppressLint
import android.graphics.PointF
import android.view.MotionEvent
import android.view.View
import kotlin.math.sqrt

@SuppressLint("ClickableViewAccessibility")
class ZoomInOutView(private var view: View?) {

    private var lastEvent: FloatArray? = null
    private var isZoomAndRotate = false
    private var isOutSide = false
    private val noAction = 0
    private var mode = noAction
    private val mid = PointF()
    private val actionDrag = 1
    private val actionZoomIn = 2
    private var xCoOrdinate = 0f
    private var yCoOrdinate = 0f
    private var vewOriginalX: Float = 0f
    private var vewOriginalY: Float = 0f
    var oldDist = 1f

    fun setOriginalValue() {
        if (view != null) {
            vewOriginalX = view!!.x
        }
        if (view != null) {
            vewOriginalY = view!!.y
        }
    }


    fun setView(view: View) {
        this.view = view

        view.setOnTouchListener { v: View, event ->
            viewTransformation(v, event)
            true
        }
    }

    private fun viewTransformation(view: View, event: MotionEvent) {
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                xCoOrdinate = view.x - event.rawX
                yCoOrdinate = view.y - event.rawY
                isOutSide = false
                mode = actionDrag
                lastEvent = null
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                oldDist = spacing(event)
                if (oldDist > 10f) {
                    midPoint(mid, event)
                    mode = actionZoomIn
                }
                lastEvent = FloatArray(4)
                lastEvent!![0] = event.getX(0)
                lastEvent!![1] = event.getX(1)
                lastEvent!![2] = event.getY(0)
                lastEvent!![3] = event.getY(1)
            }
            MotionEvent.ACTION_UP -> {
                isZoomAndRotate = false
                isOutSide = true
                mode = noAction
                lastEvent = null
                mode = noAction
                lastEvent = null
            }
            MotionEvent.ACTION_OUTSIDE -> {
                isOutSide = true
                mode = noAction
                lastEvent = null
                mode = noAction
                lastEvent = null
            }
            MotionEvent.ACTION_POINTER_UP -> {
                mode = noAction
                lastEvent = null
            }
            MotionEvent.ACTION_MOVE -> if (!isOutSide) {

                if (mode == actionDrag) {
                    isZoomAndRotate = false
                    view.animate().x(event.rawX + xCoOrdinate).y(event.rawY + yCoOrdinate)
                        .setDuration(0).start()
                }

                if (mode == actionZoomIn && event.pointerCount == 2) {
                    val newDist1 = spacing(event)
                    if (newDist1 > 10f) {
                        val scale = newDist1 / oldDist * view.scaleX
                        if (scale >= 1) {
                            view.scaleX = scale
                            view.scaleY = scale
                        }
                    }
                }
            }
        }
    }

    private fun spacing(event: MotionEvent): Float {
        val x = event.getX(0) - event.getX(1)
        val y = event.getY(0) - event.getY(1)
        return sqrt((x * x + y * y).toDouble()).toFloat()
    }

    private fun midPoint(point: PointF, event: MotionEvent) {
        val x = event.getX(0) + event.getX(1)
        val y = event.getY(0) + event.getY(1)
        point[x / 2] = y / 2
    }
}
