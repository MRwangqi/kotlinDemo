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

/**
 * @author wangqi
 * @since 2017/12/2 13:02
 */
class PieChatView : View {


    constructor(context: Context) : this(context, null) {}

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}


    /**
     * view的宽度
     */
    var width: Float = 0f
    /**
     * view的高度
     */
    var height: Float = 0f

    /**
     * drawArc距离左边的距离
     */
    var left: Float = 0f
    /**
     * drawArc距离上边的距离
     */
    var top: Float = 0f
    /**
     * drawArc距离右边的距离
     */
    var right: Float = 0f
    /**
     * drawArc距离下边的距离
     */
    var bottom: Float = 0f

    /**
     * 个数分类集合
     */
    var pieList = arrayListOf(10f, 3f, 7f)

    /**
     * 饼图所占的比例
     */
    var scaleList = arrayListOf<Float>()

    /**
     * 个数分类的总量
     */
    var total: Float = 0f

    /**
     * 记录当前画饼图的度数
     */
    var currentDegree: Float = 0f

    /**
     * 累加饼图的度数作为下一个绘制的起始度数
     */
    var srctorDegree: Float = 0f

    /**
     *  横线的长度
     */
    var lineae: Int = 30

    /**
     * 斜线的长度
     */
    var slantLine: Int = 30


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (scale in scaleList) {
            val paint = Paint()
            paint.strokeWidth = dip(10.0f).toFloat()
            paint.isAntiAlias = true
            val hex = "#" + Integer.toHexString((-16777216 * Math.random()).toInt())

            paint.color = Color.parseColor(hex)

            srctorDegree = scale * 360

            canvas.drawArc(left, top, right, bottom, currentDegree, srctorDegree, true, paint)

            val path = Path()
            path.arcTo(left, top, right, bottom, currentDegree + srctorDegree / 2, 0f, false)

            val bounds = RectF()
            path.computeBounds(bounds, true)

            //获取当前的百分比文字
            val textStr = String.format("%.2f%%", scale * 100)
            //获取文字的宽度
            val textWidth = getStringWidth(textStr)

            /*
             * 对当前的path位置进行判断
             * 把整个饼图分成一个象限
             * 第一、二、三、四象限的文字朝右上、左上、左下、右下
             */
            //第一象限
            if (bounds.left >= width / 2 && bounds.top <= width / 2) {
                path.lineTo(bounds.left + lineae, bounds.top)
                path.lineTo(bounds.left + lineae + slantLine, bounds.top - slantLine)
                canvas.drawPath(path, paintLine)
                canvas.drawText(textStr, bounds.left + lineae + slantLine, bounds.top - slantLine, paintText)
                //第二象限
            } else if (bounds.left <= width / 2 && bounds.top <= width / 2) {
                path.lineTo(bounds.left - lineae, bounds.top)
                path.lineTo(bounds.left - lineae - slantLine, bounds.top - slantLine)
                canvas.drawPath(path, paintLine)
                canvas.drawText(textStr, bounds.left - lineae - slantLine - textWidth, bounds.top - slantLine, paintText)
                //第三象限
            } else if (bounds.left <= width / 2 && bounds.top >= width / 2) {
                path.lineTo(bounds.left - lineae, bounds.top)
                path.lineTo(bounds.left - lineae - slantLine, bounds.top + slantLine)
                canvas.drawPath(path, paintLine)
                canvas.drawText(textStr, bounds.left - lineae - slantLine - textWidth, bounds.top + lineae, paintText)
                //第四象限
            } else {
                path.lineTo(bounds.left + lineae, bounds.top)
                path.lineTo(bounds.left + lineae + slantLine, bounds.top + slantLine)
                canvas.drawPath(path, paintLine)
                canvas.drawText(textStr, bounds.left + lineae + slantLine, bounds.top + slantLine, paintText)
            }
            //累加弧度值
            currentDegree += srctorDegree
        }

        canvas.drawCircle(width / 2, width / 2, width / 8, paintCicle)
    }

    var paintLine = Paint()

    var paintText = Paint()

    var paintCicle = Paint()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setBackgroundColor(resources.getColor(R.color.black))
        width = w.toFloat()
        height = h.toFloat()

        left = width / 4f
        top = width / 4f
        right = width - left
        bottom = width - top

        lineae = (width / 30f).toInt()
        slantLine = (width / 40f).toInt()



        paintLine.color = resources.getColor(R.color.white)
        paintLine.strokeWidth = dip(1.0f).toFloat()
        paintLine.isAntiAlias = true
        paintLine.textSize = dip(width / 100).toFloat()
        paintLine.style = Paint.Style.STROKE


        paintText.color = resources.getColor(R.color.white)
        paintText.strokeWidth = dip(1.0f).toFloat()
        paintText.isAntiAlias = true
        paintText.textSize = dip(width / 100).toFloat()
        paintText.style = Paint.Style.FILL



        paintCicle.color = resources.getColor(R.color.black)
        paintCicle.strokeWidth = dip(1.0f).toFloat()
        paintCicle.isAntiAlias = true
        paintCicle.textSize = dip(width / 100).toFloat()
        paintCicle.style = Paint.Style.FILL



        total = pieList.sum()
        for (a in pieList) {
            scaleList.add(a.div(total))
        }
    }

    /**
     * 获取文字的宽度
     */
    private fun getStringWidth(str: String): Float = paintLine.measureText(str)

    /**
     * 设置扇形参数
     */
    fun setPieData(a: ArrayList<Float>) {
        pieList.clear()
        pieList.addAll(a)
        invalidate()
    }

}