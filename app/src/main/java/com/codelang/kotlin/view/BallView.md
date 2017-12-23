## 用kotlin来实现一个打方块的小游戏

> 前言

今天来做个打方块的小游戏，继续熟悉kotlin的语法,看下要实现的效果图

![](http://oxp6pf88h.bkt.clouddn.com/bricksbrick.gif)

看着效果图好像挺难的样子，但理清思绪后，你会发现特别的简单，还是那句话，学习方法最重要

> 思路

- 构造界面 : 
  
  这个部分比较简单，根据控件的比例来画小球、挡板和击打的方块，所有击打的方块存储在一个集合里面，方块里面存储的信息有left、top、right、bottom位置信息和是否被击打过了的标志
  
- 挡板的滑动 :

  下面的挡板需要根据手势的左右移动来反弹小球，所以，我们可以重写onTouch来实现
  
- 小球的运动 : 
  
  我们在线程里面开启一个white循环，不停的改变小球的位置，然后重绘界面，小球的运动是有规则的，碰到四周的界面要回弹，碰到击打的方块要回弹，碰到挡板也要回弹，那么，如何回弹呢？我们给小球做一个累加值，让小球不停的去加这个值，碰到碰撞物我们就给这个累加值取反，举个例子，现在offsetX是一个正整数，那么ballX+=offsetX，现在小球是往右移动，当碰撞到最右边的时候，我们给offsetX取反，也就是offsetX=offsetX*-1，这时候offsetX变成了一个负数，那么小球ballX+=offset就会越加越少，也就是往左移动，移动到最左边的时候我们又给offsetX=offsetX*-1，这时候offsetX又变回了正数，这时候，来回的反弹就实现了，ballY的移动也是如此
  
- 小球击打方块 :

  小球击打到方块有四个方向：左、上、右、下，我们就说说击打下方的判断吧，小球顶部碰撞到方块的区域为方块的left和right区域，并且当小球的顶部刚好突破方块的bottom位置时，算是一次有效的碰撞，然后我们给这次碰撞做一个标记，然后反弹小球，下次做碰撞的时候我们忽略已经碰撞过的地方，并且不绘制碰撞过的区域
  
- 游戏结束 :

  在每次循环结束时都去统计集合里碰撞标志数量是否等于集合的size，是的话就结束循环，游戏结束
  
  
思路整理清晰后，我们来一一实现  
  
> 构造界面

首先来绘制一下小球和挡板

```
    var width: Float = 0f
    var height: Float = 0f

    /**
     * 移动滑块的宽度
     */
    var boardWdith: Float = 0f
    /**
     * 挡板的高度
     */
    var boardHeight: Float = 0f
    /**
     * 挡板距离顶部的距离
     */
    var board2Top: Float = 0f

    /**
     * 挡板距离左边的距离
     */
    var board2Left: Float = 0f
    /**
     * 小球的半径
     */
    var ballRadius: Float = 0f
    
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
        
        
        ballPaint.style = Paint.Style.FILL
        ballPaint.isAntiAlias = true
        ballPaint.color = resources.getColor(R.color.colorAccent)

        boardPaint.style = Paint.Style.STROKE
        boardPaint.isAntiAlias = true
        boardPaint.strokeWidth = dip(10).toFloat()
        boardPaint.color = resources.getColor(R.color.colorPrimary)

    }
    
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setBackgroundColor(resources.getColor(R.color.black))

        canvas.drawLine(board2Left, board2Top, board2Left + boardWdith, board2Top, boardPaint)
        canvas.drawCircle(ballX, ballY, ballRadius, ballPaint)
    }   
    
```
  ok,挡板和小球已经画好了
 
![](http://oxp6pf88h.bkt.clouddn.com/bricksbrick1.png)
 
  然后，我们来画一下被击打的方块，首先定义一个存储方块信息的Bean类
  
```
/**
 * @author wangqi
 * @since 2017/12/10 17:26
 */

public class Brick {
    /**
     * 存储方块的颜色
     */
    private String color;
    /**
     * 存储方块的坐标
     */
    private RectF rectF;
    /**
     * 判断是否碰撞到了，默认为false未碰撞
     */
    private boolean isImpact;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public RectF getRectF() {
        return rectF;
    }

    public void setRectF(RectF rectF) {
        this.rectF = rectF;
    }


    public boolean isImpact() {
        return isImpact;
    }

    public void setImpact(boolean impact) {
        isImpact = impact;
    }

}

```
  
然后我们来看看怎么绘制

```
    /**
     * 定义一个存储方块的集合
     */
    var brickList: MutableList<Brick> = mutableListOf()
    /**
     * 方块的宽度
     */
    var brickWidth = 0f
    /**
     * 方块的高度
     */
    var brickHeight = 0f   
   
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        ...
        
         //方块的宽度是view的1/5
         brickWidth = width / 5
         //方块的高度是宽度的一半
         brickHeight = brickWidth / 2       
        
        /*初始化方块  设置一个三行四列的方块*/
        for (row in 0..3) {
            for (col in 0..4) {
                createBricks(row, col)
            }
        }
        
        paintLine.strokeWidth = dip(1.0f).toFloat()
        paintLine.isAntiAlias = true
        paintLine.textSize = dip(width / 50).toFloat()
        paintLine.style = Paint.Style.FILL
        
    }   

    /**
     * 创建方块
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
```

ok,方块完美的绘制

![](http://oxp6pf88h.bkt.clouddn.com/bricksbrick2.png)


> 挡板的滑动

挡板的滑动部分，我们只需要重写onTouch方法，然后再每次move的过程中去改变挡板距离View左边界的距离

```

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

```

![](http://oxp6pf88h.bkt.clouddn.com/bricksbricks3.gif)




> 小球的运动

小球的运动是这里面最核心的部分了，我们得细细的讲讲

首先，我们需要定义一个线程，在线程里面定义一个while循环，sleep50毫秒去重回界面，所以，我们要在这50毫秒的时间里，去改变小球的运动轨迹、边界值情况、是否碰撞到方块、是否碰撞到挡板和游戏是否结束，我们先把小球给运动起来再说

```
    /**
     * 结束循环的标志位
     */
    var isOver: Boolean = false
    /**
     * 小球x方向每次移动的偏移量
     */
    var vx: Float = 8f
    /**
     * 小球y方向每次移动的偏移量
     * 默认为负数，因为小球是向上运动   
     */
    var vy: Float = -8f
    
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        ...
        
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

                Thread.sleep(50)
                postInvalidate()
            }
        }.start()

    }

```

小球开始运动了，咦，小球怎么突然不见了，哈哈，因为被方块遮挡住了

![](http://oxp6pf88h.bkt.clouddn.com/bricksbricks4.gif)


小球移动解决了，接下来我们来处理下小球弹到挡板反弹

```
        //开启线程
        thread {
            while (!isOver) {
            //边界值判断
            ...
                /*
                判断小球是否落在滑块上
                小球x轴的中心大于挡板的left并且小球x轴中心小于挡板的右边并且小球的y轴中心加上半径加上挡板高度的一半
                 */
                if (ballX >= board2Left && ballX <= board2Left + boardWdith
                        && ballY >= board2Top - ballRadius - dip(10).toFloat() / 2
                        ) {
                    //改变Y轴的运动方向    
                    vy *= -1
                }
            ...
            
            }        
        }
```

![](http://oxp6pf88h.bkt.clouddn.com/bricksbricks5.gif)


挡板的判断知道了，那么小球和方块的碰撞也就自然清晰了

```
        //开启线程
        thread {
            while (!isOver) {
            //判断小球是否落在滑块上
            ...

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
                        判断小球是否撞击到方块的底部
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
                
            ...
            
            }        
        }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        ...
        
        if (isOver) {
            val text = "通关成功"
            //获取文字的宽度，目的是为了文字居中
            val textWidth = paintLine.measureText(text)
            canvas.drawText(text, width / 2 - textWidth / 2, 100, paintLine)
        }
        
    }

```

最终效果图

![](http://oxp6pf88h.bkt.clouddn.com/bricksbrick.gif)

通过成功

![](http://oxp6pf88h.bkt.clouddn.com/bricksbricks6.png)

> 总结

理论和实践相辅相成，理论是规划实践的实施性，实践是为了证明理论


<center><p>关注公众号codelang<p>![](http://oxp6pf88h.bkt.clouddn.com/%E5%BE%AE%E4%BF%A1%E4%BA%8C%E7%BB%B4%E7%A0%81zing.png)</center>