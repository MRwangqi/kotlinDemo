package com.codelang.kotlin.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.codelang.kotlin.R
import org.jetbrains.anko.dip
import android.graphics.BlurMaskFilter




/**
 * @author wangqi
 * @since 2017/12/6 12:00
 */

class ColorFilterView : View {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var paint = Paint()
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.style = Paint.Style.STROKE


        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.batman)



        val lightingColorFilter = LightingColorFilter(0x00ffff, 0x000000)
        paint.colorFilter = lightingColorFilter
        paint.isFilterBitmap = true
        paint.textSize=dip(17).toFloat()

        canvas.drawBitmap(bitmap, bitmap.width.toFloat(), bitmap.height.toFloat(), paint)

        //添加虚线
        val pathEffect = DashPathEffect(floatArrayOf(10f, 5f), 10f)
        paint.pathEffect = pathEffect

        //添加折现拐角为弧状
//        val pathEffect = CornerPathEffect(20f)


        //设置blur
        paint.maskFilter = BlurMaskFilter(5f, BlurMaskFilter.Blur.NORMAL)
        canvas.drawCircle(300f, 300f, 200f, paint)


        //设置字体阴影
        paint.setShadowLayer(10f,10f,10f, Color.RED)
        canvas.drawText("shadow",300f,700f,paint)


    }

}
