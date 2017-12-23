package com.codelang.kotlin.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import org.jetbrains.anko.dip

import android.text.Layout
import android.R.id.text1
import android.annotation.TargetApi
import android.graphics.*
import android.os.Build
import android.text.StaticLayout
import android.text.TextPaint
import java.util.*


/**
 * @author wangqi
 * @since 2017/12/6 15:02
 */

class TextView : View {


    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    val paint = Paint()

    val paintText = Paint()

    val path = Path()
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        path.moveTo(width / 4, height / 2)

        path.lineTo(width / 2, height / 4)

        path.lineTo(width / 4 * 3, height / 2)

        paint.typeface = Typeface.createFromAsset(context.assets, "mimi.ttf")
        canvas.drawPath(path, paint)
        canvas.drawTextOnPath("你说是就大大大所多所多多所多所", path, 0f, 0f, paintText)
        canvas.drawText("CodeLang就大大", 0f, height / 4 * 3, paintText)


        var textPatin = TextPaint()
        textPatin.color = Color.RED
        textPatin.textSize = dip(30).toFloat()


        /*
             可以换行的文字
         */
        val text2 = "a\n大大所多\n大萨达\njklm\nnopqrst\nuvwx\nyz"
        val staticLayout = StaticLayout(text2, textPatin, 600,
                Layout.Alignment.ALIGN_NORMAL, 1f, 0f, true)
        canvas.save()
        staticLayout.draw(canvas)
        canvas.restore()


    }

    var width: Float = 0f
    var height: Float = 0f
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        width = w.toFloat()
        height = h.toFloat()

        paint.textSize = 20f
        paint.strokeWidth = 20f
        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true
        paint.color = Color.RED
//        paint.strokeCap=Paint.Cap.ROUND//圆头
        paint.strokeCap = Paint.Cap.SQUARE//方头
        paint.strokeJoin = Paint.Join.ROUND  //折线拐角为圆


        paintText.textSize = dip(30).toFloat()
        paintText.style = Paint.Style.FILL
        paintText.isAntiAlias = true
        paintText.color = Color.RED
        //设置伪加粗
//        paintText.isFakeBoldText = true
        //删除线
//        paintText.isStrikeThruText = true
        //添加下划线
//        paintText.isUnderlineText=true
        //字体倾斜
//        paintText.textSkewX=1f
        //设置字体间距，API最小为21
        paintText.letterSpacing=0.2f
        //用 CSS 的 font-feature-settings 的方式来设置文字
        paintText.fontFeatureSettings = "smcp"
        //设置字体的对齐方式
        paint.textAlign = Paint.Align.CENTER
        paint.textLocale= Locale.JAPANESE
    }

}
