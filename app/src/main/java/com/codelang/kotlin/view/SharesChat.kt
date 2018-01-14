package com.codelang.kotlin.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.codelang.kotlin.model.Shares
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.dip

/**
 * @author wangqi
 * @since 2018/1/13 18:51
 */

class SharesChat : View {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}


    val yinPaint = Paint()

    val yanPaint = Paint()

    val totalPaint = Paint()

    var currentPaint = yinPaint

    var height = 0f

    var width = 0f

    val list = arrayListOf<Shares>()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        height = h.toFloat()
        width = w.toFloat()

        yinPaint.strokeWidth = dip(2).toFloat()
        yinPaint.style = Paint.Style.FILL
        yinPaint.color = Color.BLUE
        yinPaint.isAntiAlias = true
        yinPaint.textSize = dip(15).toFloat()


        yanPaint.strokeWidth = dip(2).toFloat()
        yanPaint.style = Paint.Style.STROKE
        yanPaint.color = Color.RED


        totalPaint.style = Paint.Style.FILL
        totalPaint.strokeWidth = dip(5).toFloat()



        for (i in 1..5) {
            val shares = Shares(800f - i * 50, 700f - i * 50, 600f - i * 50, 500f - i * 50, i * 50f)
            list.add(shares)
        }

        for (i in 1..5) {
            val shares = Shares(800f - (5 - i) * 50, 700f - (5 - i) * 50, 600f - (5 - i) * 50, 500f - (5 - i) * 50, (5 - i) * 50f)
            list.add(shares)
        }


    }

    /**
     * 开盘价大于收盘价   阴线  蓝色
     * 收盘价大于开盘价   阳线
     */

    var topPrice = 800f//最高价100块
    var openPrice = 700f//开盘价 80块
    var receivePrice = 600f//收盘价
    var bottomPrice = 500f//最低价
    var widthPrice = dip(5).toFloat()//阴线宽度
    var currentPosition = 200f//日期


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        for (i in list.indices) {
            if (i % 2 == 0) {
                totalPaint.color = Color.BLUE
                currentPaint = yinPaint
            } else {
                totalPaint.color = Color.RED
                currentPaint = yanPaint
            }
            yinCanvas(list[i], canvas)
            currentPosition += 100f
        }
        drawScale(canvas)

    }


    fun yinCanvas(shares: Shares, canvas: Canvas) {
        canvas.drawLine(currentPosition, height - shares.bottomPrice, currentPosition, height - shares.receivePrice, currentPaint)
        val rectF = RectF()
        rectF.left = currentPosition - widthPrice
        rectF.bottom = height - shares.receivePrice
        rectF.right = currentPosition + widthPrice
        rectF.top = height - shares.openPrice
        canvas.drawRect(rectF, currentPaint)
        canvas.drawLine(currentPosition, rectF.top, currentPosition, height - shares.topPrice, currentPaint)


        canvas.drawLine(currentPosition, height, currentPosition, height - shares.total, totalPaint)

    }


    fun drawScale(canvas: Canvas) {
        for (i in 1..height.toInt()) {
            if (i % 100 == 0) {
                canvas.drawLine(0f, height - i, dip(10).toFloat(), height - i, yinPaint)
                canvas.drawText(i.toString(), dip(10).toFloat(), height - i, yinPaint)
            }
        }

        for (i in 1..width.toInt()) {
            if (i % 100 == 0) {
                canvas.drawLine(i.toFloat(), height, i.toFloat(), height - dip(10).toFloat(), yinPaint)
            }
        }


    }

}
