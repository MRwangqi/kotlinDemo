package com.codelang.kotlin.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator
import com.codelang.kotlin.R
import org.jetbrains.anko.dip


/**
 * @author wangqi
 * @since 2017/12/8 13:32
 */

class PathMeasureView : View {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}


    val list = arrayListOf<PointF>()
    var path2 = Path()


    var over: Float = 0f

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        val path = Path()

        path.moveTo(0f, height / 2)


        path.quadTo(width / 2, over, width, height / 2)

//        path.moveTo(0f, height / 2)
//        path.quadTo(width / 2, height / 6, width, height / 2)
//        val measure = PathMeasure(path, false)
//        //利用PathMeasure分别测量出各个点的坐标值coords
//        for (c in 0..measure.length.toInt()) {
//            measure.getPosTan(c.toFloat(), pos, tan)
//            val p = PointF()
//            p.x = pos[0]
//            p.y = pos[1]
//
//            list.add(p)
//            path2.lineTo(pos[0], pos[1])
//        }

//        paint.color = resources.getColor(R.color.colorPrimary)
        canvas.drawPath(path, paint)

    }


    var flag: Float = 1f
    public fun anim() {
        var oa = ValueAnimator.ofInt(100)
        oa.interpolator = AccelerateInterpolator()
        oa.addUpdateListener { animation ->
            var index = animation.animatedValue as Int
            flag = (flag * -1)


            over = height / 2 + (100f - index) * flag
            invalidate()
        }
        oa.duration = 5000

        oa.start()
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.i("tag", "ACTION_DOWN")
            }
            MotionEvent.ACTION_MOVE -> {
                over = event.y
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                anim()
            }
        }
        return true
    }


    var width: Float = 0f
    var height: Float = 0f

    var paint = Paint()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        width = w.toFloat()
        height = h.toFloat()


        over = height / 2
        paint.isAntiAlias = true
        paint.textSize = dip(width / 100).toFloat()
        paint.color = resources.getColor(R.color.colorAccent)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = dip(10).toFloat()
    }
}
