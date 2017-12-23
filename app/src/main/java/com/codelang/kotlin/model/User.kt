package com.codelang.kotlin.model

/**
 * @author wangqi
 * @since 2017/12/1 10:11
 */

data class User(val x: Int, val y: Int) {
    override fun toString(): String {
        return "x=" + x +"  y="+ y
    }
}