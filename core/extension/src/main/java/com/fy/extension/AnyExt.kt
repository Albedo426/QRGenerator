package com.fy.extension

import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.view.Gravity
import android.view.View
import androidx.core.graphics.ColorUtils

fun Any.generateBackgroundWithShadowWithValue(
    view: View,
    backgroundColorValue: Int,
    cornerRadiusValue: Float,
    shadowColorValue: Int,
    elevationValue: Int,
    shadowGravity: Int
) : Drawable {
    val outerRadius = floatArrayOf(
        cornerRadiusValue, cornerRadiusValue, cornerRadiusValue,
        cornerRadiusValue, cornerRadiusValue, cornerRadiusValue, cornerRadiusValue,
        cornerRadiusValue
    )
    val backgroundPaint = Paint()
    backgroundPaint.style = Paint.Style.FILL
    backgroundPaint.setShadowLayer(cornerRadiusValue, 0F, 10F, 0)
    val shapeDrawablePadding = Rect()
    shapeDrawablePadding.left = elevationValue
    shapeDrawablePadding.right = elevationValue
    when (shadowGravity) {
        Gravity.FILL -> {
            shapeDrawablePadding.left = elevationValue * 5
            shapeDrawablePadding.top = elevationValue
            shapeDrawablePadding.right = elevationValue * 5
            shapeDrawablePadding.bottom = elevationValue * 2
        }
        Gravity.CENTER -> {
            shapeDrawablePadding.top = elevationValue
            shapeDrawablePadding.bottom = elevationValue
        }
        Gravity.TOP -> {
            shapeDrawablePadding.top = elevationValue * 2
            shapeDrawablePadding.bottom = elevationValue
        }
        Gravity.BOTTOM -> {
            shapeDrawablePadding.top = elevationValue
            shapeDrawablePadding.bottom = elevationValue * 2
        }
        else -> {
            shapeDrawablePadding.top = elevationValue
            shapeDrawablePadding.bottom = elevationValue * 2
        }
    }

    val shapeDrawable = ShapeDrawable()
    shapeDrawable.setPadding(shapeDrawablePadding)
    shapeDrawable.paint.color = backgroundColorValue
    shapeDrawable.paint.setShadowLayer(
        cornerRadiusValue, 0F, 0F, ColorUtils.setAlphaComponent(
            shadowColorValue,
            20
        )
    )
    view.setLayerPaint(shapeDrawable.paint)
    shapeDrawable.shape = RoundRectShape(outerRadius, null, null)
    val drawable = LayerDrawable(arrayOf<Drawable>(shapeDrawable))
    drawable.setLayerInset(
        0,
        elevationValue * 5,
        elevationValue * 5,
        elevationValue * 5,
        elevationValue * 5
    )

    return drawable
}