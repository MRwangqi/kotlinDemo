package com.codelang.kotlin.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.codelang.kotlin.R
import com.codelang.kotlin.model.Brick
import com.codelang.kotlin.model.User
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.dip
import kotlin.concurrent.thread
import kotlin.math.log

/**
 * @author wangqi
 * @since 2017/12/10 16:03
 */

class BallView : View {

    var width: Float = 0f
    var height: Float = 0f

    /**
     * 移动滑块的宽度
     */
    var boardWdith: Float = 0f
    /**
     * 移动滑块的高度
     */
    var boardHeight: Float = 0f
    /**
     * 面板距离顶部的距离
     */
    var board2Top: Float = 0f

    /**
     * 面板距离左边的距离
     */
    var board2Left: Float = 0f
    /**
     * 小球的半径
     */
    var ballRadius: Float = 0f

    /**
     * 小球X轴的距离
     */
    var ballX: Float = 0f
    /**
     * 小球Y轴的距离
     */
    var ballY: Float = 0f
    /**
     * 小球x方向的速度
     */
    var vx: Float = 8f
    /**
     * 小球y方向的速度
     */
    var vy: Float = -8f

    /**
     * brick块的宽度
     */
    var brickWidth = 0f
    /**
     * brick块的高度
     */
    var brickHeight = 0f

    /**
     * 结束循环的标志位
     */
    var isOver: Boolean = false

    /**
     * 定义一个存储方块的集合
     */
    var brickList: MutableList<Brick> = mutableListOf()

    val ballPaint = Paint()
    val boardPaint = Paint()
    val paintLine = Paint()

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        width = w.toFloat()
        height = h.toFloat()

        //挡板的宽度
        boardWdith = width / 8
        //挡板距离顶部的距离
        board2Top = height / 8 * 7
        //挡板的left距离左边的距离，目的使挡板居中
        board2Left = width / 2 - boardWdith / 2
        //设置小球的半径为挡板的1/4
        ballRadius = boardWdith / 4
        //设置小球的x和y坐标
        ballX = width / 2
        ballY = board2Top - ballRadius - dip(10).toFloat() / 2

        //方块的宽度
        brickWidth = width / 5
        //方块的高度
        brickHeight = brickWidth / 2

        ballPaint.style = Paint.Style.FILL
        ballPaint.isAntiAlias = true
        ballPaint.color = resources.getColor(R.color.colorAccent)

        boardPaint.style = Paint.Style.STROKE
        boardPaint.isAntiAlias = true
        boardPaint.strokeWidth = dip(10).toFloat()
        boardPaint.color = resources.getColor(R.color.colorPrimary)


        paintLine.color = resources.getColor(R.color.white)
        paintLine.strokeWidth = dip(1.0f).toFloat()
        paintLine.isAntiAlias = true
        paintLine.textSize = dip(width / 50).toFloat()
        paintLine.style = Paint.Style.FILL


        /*初始化滑块*/
        for (row in 0..3) {
            for (col in 0..4) {
                createBricks(row, col)
            }
        }


        //开启线程
        thread {
            while (!isOver) {

                ballX += vx
                ballY += vy

                /*
                   边界值判定
                   如果小球小于左边界或大于右边界则x方向取反
                 */
                if (ballX + ballRadius > width || ballX - ballRadius < 0) {
                    vx *= -1
                }
                /*
                   边界值判定
                   如果小球大于底部边界或小于顶部边界则Y方向取反
                 */
                if (ballY - ballRadius < 0 || ballY + ballRadius > height) {
                    vy *= -1
                }


                /*
                判断小球是否落在滑块上
                 */
                if (ballX >= board2Left && ballX <= board2Left + boardWdith
                        && ballY >= board2Top - ballRadius - dip(10).toFloat() / 2
                        ) {
                    vy *= -1
                }
//
//
                /*
                 * 循环集合的每一个方块，判断小球当前的位置是否碰撞到方块
                 */
                for (i in brickList.indices) {
                    //拿到方块
                    val brick = brickList[i]

                    //忽略撞击过的方块
                    if (brick.isImpact) {
                        continue
                    }
                    //获取方块的坐标
                    val rectF = brick.rectF

                    /*
                        判断小球是否撞击到方块
                        小球x轴的中心大于方块的left
                        小球x轴的中心小于方块的right
                        小球y轴中心减去半径，也就是小球的顶部，是否小于等于方块的底部，也就是穿过方块底部的一瞬间
                     */
                    if (ballX >= rectF.left && ballX <= rectF.right && ballY - ballRadius <= rectF.bottom) {
                        //设置该方块已被撞击
                        brick.isImpact = true
                        //方向取反
                        vy *= -1
                    }
                }
                /*
                 * 统计被撞击方块的数量是否等于集合，是的话表明游戏结束，设置结束标志位，停止while循环
                 */
                if (brickList.count { it.isImpact } == brickList.size) {
                    isOver = true
                }


                Thread.sleep(50)
                postInvalidate()
            }
        }.start()

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setBackgroundColor(resources.getColor(R.color.black))

        canvas.drawLine(board2Left, board2Top, board2Left + boardWdith, board2Top, boardPaint)
        canvas.drawCircle(ballX, ballY, ballRadius, ballPaint)


        for (i in brickList.indices) {
            val brick = brickList[i]
            //如果未碰撞到
            if (!brick.isImpact) {
                paintLine.color = Color.parseColor(brick.color)
                canvas.drawRect(brick.rectF, paintLine)
            }
        }



        if (isOver) {
            val text = "通关成功"
            //获取文字的宽度，目的是为了文字居中
            val textWidth = paintLine.measureText(text)
            canvas.drawText(text, width / 2 - textWidth / 2, 100.0f, paintLine)
        }


    }

    /**
     * 创建bricks块
     */
    fun createBricks(row: Int, col: Int) {
        var brick = Brick()
        var rectF = RectF()
        rectF.left = brickWidth * col
        rectF.top = brickHeight * row
        rectF.right = brickWidth * (col + 1)
        rectF.bottom = brickHeight * (row + 1)

        brick.rectF = rectF
        val hex = "#" + Integer.toHexString((-16777216 * Math.random()).toInt())
        brick.color = hex

        brickList.add(brick)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }

            MotionEvent.ACTION_MOVE -> {
                board2Left = event.x - boardWdith / 2
                invalidate()
            }

            MotionEvent.ACTION_UP -> {

            }
        }
        return true
    }


}
