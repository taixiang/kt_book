package com.dingshuwang.util

import android.content.Context

object DensityUtils {
    /**将像素转换为对应设备的density */
    fun pixelsToDp(context: Context, pixels: Int): Int {
        val scale = context.resources.displayMetrics.density
        return ((pixels - 0.5f) / scale).toInt()
    }

    fun getDensity(context: Context): Float {
        return context.resources.displayMetrics.density
    }

    /**将dp值转换为对应的像素值 */
    fun dpToPx(context: Context, dp: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    /**将sp值转换为对应的像素值，主要用于TextView的字体中 */
    fun spToPx(context: Context, sp: Float): Int {
        return (sp * context.resources.displayMetrics.scaledDensity).toInt()
    }

    /**将像素值值转换为对应的sp值，主要用于TextView的字体中 */
    fun pxToSp(context: Context, pixels: Int): Int {
        return (pixels / context.resources.displayMetrics.scaledDensity).toInt()
    }

    fun getScreenWidthInPx(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    fun getScreenHeightInPx(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }
}
