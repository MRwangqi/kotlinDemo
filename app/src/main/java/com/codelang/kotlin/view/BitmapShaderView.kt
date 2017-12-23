package com.codelang.kotlin.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.codelang.kotlin.R
import org.jetbrains.anko.dip
import android.graphics.Shader
import android.graphics.Color.LTGRAY
import android.graphics.LinearGradient




/**
 * @author wangqi
 * @since 2017/12/6 10:22
 */

class BitmapShaderView : View {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    private val paint = Paint()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setBackgroundColor(Color.BLACK)

        val bitmap1 = BitmapFactory.decodeResource(resources, R.mipmap.batman)

        val bitmap2 = BitmapFactory.decodeResource(resources, R.mipmap.aiche_def)

        //横向效果、纵向效果
        val shader1 = BitmapShader(bitmap1, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
        val shader2 = BitmapShader(bitmap2, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)

        // 创建LinearGradient并设置渐变颜色数组,平铺效果为镜像
      val  mLinearGradient = LinearGradient(0f, 0f, 0f, 100f, intArrayOf( Color.BLUE, Color.GREEN), null,
                Shader.TileMode.MIRROR)

        val compossShader = ComposeShader(shader1, mLinearGradient, PorterDuff.Mode.DARKEN)

//        val matrix = Matrix()
//        // 设置矩阵变换
//        matrix.setRotate(100f)
//        // 设置Shader的变换矩阵
//        shader.setLocalMatrix(matrix)
        paint.shader = compossShader
//        canvas.drawCircle(width / 2, height / 2, width/2, paint)


        canvas.drawRect(0f, 0f, width, height, paint)

        canvas.drawText("hahah", width / 2, height / 2, paint)
    }


    var width: Float = 0f
    var height: Float = 0f
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        width = w.toFloat()
        height = h.toFloat()

        paint.color = Color.RED
        paint.style = Paint.Style.FILL
        paint.textSize = dip(20).toFloat()
        paint.isAntiAlias = true
    }
}
