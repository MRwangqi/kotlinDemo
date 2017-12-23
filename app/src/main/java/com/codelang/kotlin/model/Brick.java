package com.codelang.kotlin.model;

import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangqi
 * @since 2017/12/10 17:26
 */

public class Brick {
    private String color;
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
