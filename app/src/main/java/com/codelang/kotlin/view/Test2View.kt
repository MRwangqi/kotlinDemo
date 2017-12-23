package com.codelang.kotlin.view

import android.content.Context
import android.graphics.*
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.codelang.kotlin.R
import org.jetbrains.anko.dip
import java.util.logging.Logger
import android.R.attr.path
import android.graphics.RectF
import android.R.attr.angle
import java.util.Collections.rotate
import android.R.attr.radius


/**
 * @author wangqi
 * @since 2017/12/1 13:58
 */

class Test2View : View {


    constructor(context: Context) : this(context, null) {}

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        paintLine.color = resources.getColor(R.color.white)
        paintLine.strokeWidth = dip(1.0f).toFloat()
        paintLine.isAntiAlias = true
        paintLine.textSize = dip(15f).toFloat()
        paintLine.style = Paint.Style.STROKE

        paintRed.color = resources.getColor(R.color.colorAccent)
        paintRed.strokeWidth = dip(10.0f).toFloat()
        paintRed.isAntiAlias = true


        paintYellow.color = resources.getColor(R.color.yellow)
        paintYellow.strokeWidth = dip(10.0f).toFloat()
        paintYellow.isAntiAlias = true
        paintYellow.textSize = dip(20).toFloat()

        paintPuple.color = resources.getColor(R.color.puple)
        paintPuple.strokeWidth = dip(10.0f).toFloat()
        paintPuple.isAntiAlias = true

        paintGray.color = resources.getColor(R.color.gray)
        paintGray.strokeWidth = dip(10.0f).toFloat()
        paintGray.isAntiAlias = true


        paintGreen.color = resources.getColor(R.color.green)
        paintGreen.strokeWidth = dip(10.0f).toFloat()
        paintGreen.isAntiAlias = true

        paintBlue.color = resources.getColor(R.color.blue)
        paintBlue.strokeWidth = dip(10.0f).toFloat()
        paintBlue.isAntiAlias = true

    }

    var paintLine = Paint()

    var paintRed = Paint()
    var paintYellow = Paint()
    var paintPuple = Paint()
    var paintGray = Paint()
    var paintGreen = Paint()
    var paintBlue = Paint()


    var width: Float = 0f
    var height: Float = 0f

//    var left: Float = 100f
//    var top: Float = 100f

    /**
     * 弧边距离边距的偏移量
     */
    var radius: Float = 0f
    /**
     * 饼图的右边
     */
    var right: Float = 0f
    /**
     * 饼图的下边
     */
    var bottom: Float = 0f
    /**
     * 饼图的左边
     */
    var left: Float = 0f
    /**
     * 饼图的上边
     */
    var top: Float = 0f

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        radius = width / 4f
        left = -radius
        top = -radius
        bottom = radius
        right = radius
        canvas.translate(width / 2, (height) / 2)
//        red(canvas)
//        yellow(canvas)
//        purple(canvas)
//        gray(canvas)
//        green(canvas)
//        blue(canvas)
//        canvas.drawArc(left, top, right, bottom, 0f, 360f, true, paintRed)


        val offset = 35f
        canvas.save()
        canvas.rotate(-offset)
        canvas.drawArc(left, top, right, bottom, 0f + offset, 20f, true, paintPuple)
        canvas.drawArc(left, top, right, bottom, 20f + offset, 10f, true, paintGray)
        canvas.drawArc(left, top, right, bottom, 30f + offset, 40f, true, paintGreen)
        canvas.drawArc(left, top, right, bottom, 70f + offset, 110f, true, paintBlue)
        canvas.drawArc(left, top, right, bottom, 290f + offset, 70f, true, paintYellow)
        canvas.drawArc(left, top - 30, right, bottom - 30, 180f + offset, 110f, true, paintRed)


        canvas.rotate(offset)


        val path = Path()
        path.arcTo(left, top - 30, right, bottom - 30, 180f + 110f / 2, 110f, false)
        val bounds = RectF()
        path.computeBounds(bounds, true)
        val text = "Test"
        val textWidth: Float = getStringWidth(text)
        path.lineTo(bounds.left - 100, bounds.top)
        path.lineTo(bounds.left - 150, bounds.top - 50)
        canvas.drawPath(path, paintLine)
        canvas.drawText(text, bounds.left - 150 - textWidth, bounds.top - 50, paintLine)


        canvas.restore()




//        val path = Path()
//        path.arcTo(left, top - 30, right, bottom-30, 180f, 110f, false)
//        val bounds = RectF()
//        path.computeBounds(bounds, true)
//        val text = "Test"
//        val textWidth: Float = getStringWidth(text)
//        path.lineTo(bounds.left - 100, bounds.top)
//        path.lineTo(bounds.left - 150, bounds.top - 50)
//        canvas.drawPath(path, paintLine)
//        canvas.drawText(text, bounds.left - 150 - textWidth, bounds.top - 50, paintLine)
    }


    /**
     * 画红色饼图
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun red(canvas: Canvas) {
        canvas.drawArc(left - 20, top - 20, right, bottom, 180f, 110f, true, paintRed)
        val path = Path()
        path.arcTo(left - 20, top - 20, right, bottom, 235f, 0f, false)

        val bounds = RectF()
        path.computeBounds(bounds, true)

        val text = "Test"

        val textWidth: Float = getStringWidth(text)


        path.lineTo(bounds.left - 100, bounds.top)
        path.lineTo(bounds.left - 150, bounds.top - 50)
        canvas.drawPath(path, paintLine)
        canvas.drawText(text, bounds.left - 150 - textWidth, bounds.top - 50, paintLine)

    }


    /**
     * 画黄色饼图
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun yellow(canvas: Canvas) {
        canvas.drawArc(left, top, right, bottom, 290f, 70f, true, paintYellow)
        val path = Path()
        path.arcTo(left, top, right, bottom, 325f, 0f, false)

        val bounds = RectF()
        path.computeBounds(bounds, true)

        val text = "Test"

        path.lineTo(bounds.left + 100, bounds.top)
        path.lineTo(bounds.left + 150, bounds.top + 50)
        canvas.drawPath(path, paintLine)
        canvas.drawText(text, bounds.left + 150, bounds.top + 50, paintLine)
    }

    /**
     * 紫色饼图
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun purple(canvas: Canvas) {
        canvas.drawArc(left, top, right, bottom, 0f, 20f, true, paintPuple)

        val path = Path()
        path.arcTo(left, top, right, bottom, 10f, 0f, false)

        val bounds = RectF()
        path.computeBounds(bounds, true)

        path.lineTo(bounds.left + 100, bounds.top)
        canvas.drawPath(path, paintLine)
        canvas.drawText("Test", bounds.left + 100, bounds.top, paintLine)

    }

    /**
     * 灰色饼图
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun gray(canvas: Canvas) {
        canvas.drawArc(left, top, right, bottom, 22f, 10f, true, paintGray)
        val path = Path()
        path.arcTo(left, top, right, bottom, 27f, 0f, false)

        val bounds = RectF()
        path.computeBounds(bounds, true)

        path.lineTo(bounds.left + 100, bounds.top)
        path.lineTo(bounds.left + 150, bounds.top + 100)

        canvas.drawPath(path, paintLine)
        canvas.drawText("Test", bounds.left + 150, bounds.top + 100, paintLine)
    }


    /**
     * 绿色饼图
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun green(canvas: Canvas) {
        canvas.drawArc(left, top, right, bottom, 32f, 40f, true, paintGreen)


        val path = Path()
        path.arcTo(left, top, right, bottom, 52f, 0f, false)

        val bounds = RectF()
        path.computeBounds(bounds, true)

        path.lineTo(bounds.left + 100, bounds.top)
        path.lineTo(bounds.left + 150, bounds.top + 100)

        canvas.drawPath(path, paintLine)
        canvas.drawText("Test", bounds.left + 150, bounds.top + 100, paintLine)
    }


    /**
     * 蓝色饼图
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun blue(canvas: Canvas) {
        canvas.drawArc(left - 10, top, right, bottom, 72f, 107f, true, paintBlue)


        val path = Path()
        path.arcTo(left - 10, top, right, bottom, 125f, 0f, false)

        val bounds = RectF()
        path.computeBounds(bounds, true)

        val text = "Test"

        val textWidth: Float = getStringWidth(text)


        path.lineTo(bounds.left - 100, bounds.top + 50)
        path.lineTo(bounds.left - 150, bounds.top + 50)

        canvas.drawPath(path, paintLine)
        canvas.drawText(text, bounds.left - 150 - textWidth, bounds.top + 50, paintLine)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setBackgroundColor(resources.getColor(R.color.black))
        width = w.toFloat()
        height = h.toFloat()
    }

    /**
     * 获取文字的宽度
     */
    private fun getStringWidth(str: String): Float = paintLine.measureText(str)
}
