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


/**
 * @author wangqi
 * @since 2017/12/1 13:58
 */

class TestView : View {


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
     * 圆弧距离左、右的巨鹿
     */
    var distance: Float = 0f
    /**
     * 圆弧的直径
     */
    var radius: Float = 0f


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        distance = width / 4f
        radius = width - distance
        red(canvas)
        yellow(canvas)
        purple(canvas)
        gray(canvas)
        green(canvas)
        blue(canvas)
    }


    /**
     * 画红色饼图
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun red(canvas: Canvas) {
        canvas.drawArc(distance - 20, distance - 20, radius, radius, 180f, 110f, true, paintRed)
        val path = Path()

        path.arcTo(distance - 20, distance - 20, radius, radius, 235f, 0f, false)

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
        canvas.drawArc(distance, distance, radius, radius, 290f, 70f, true, paintYellow)
        val path = Path()
        path.arcTo(distance, distance, radius, radius, 325f, 0f, false)

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
        canvas.drawArc(distance, distance, radius, radius, 0f, 20f, true, paintPuple)

        val path = Path()
        path.arcTo(distance, distance, radius, radius, 10f, 0f, false)

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
        canvas.drawArc(distance, distance, radius, radius, 22f, 10f, true, paintGray)
        val path = Path()
        path.arcTo(distance, distance, radius, radius, 27f, 0f, false)

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
        canvas.drawArc(distance, distance, radius, radius, 32f, 40f, true, paintGreen)


        val path = Path()
        path.arcTo(distance, distance, radius, radius, 52f, 0f, false)

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
        canvas.drawArc(distance - 10, distance, radius, radius, 72f, 107f, true, paintBlue)



        val path = Path()
        path.arcTo(distance, distance, radius, radius, 125f, 0f, false)

        val bounds = RectF()
        path.computeBounds(bounds, true)

        val text = "Test"

        val textWidth: Float = getStringWidth(text)


        path.lineTo(bounds.left - 100, bounds.top + 50)
        path.lineTo(bounds.left - 150, bounds.top + 50)

        canvas.drawPath(path, paintLine)
        canvas.drawText(text, bounds.left - 150-textWidth, bounds.top + 50, paintLine)
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
