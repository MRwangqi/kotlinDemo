package com.codelang.kotlin

import com.codelang.kotlin.model.Brick
import com.codelang.kotlin.model.User
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

import org.junit.Test

import java.lang.reflect.Field
import java.lang.reflect.Method
import java.util.ArrayList
import java.util.Observer

import org.junit.Assert.*
import java.util.concurrent.TimeUnit

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    @Test
    @Throws(Exception::class)
    fun addition_isCorrect() {
        assertEquals(4, (2 + 2).toLong())

//        val hex = "#" + Integer.toHexString((-16777216 * Math.random()).toInt())
//        //        System.out.print("#" + hex);
//
//
//        val a = if (java.lang.Long.valueOf("12") === java.lang.Long.valueOf("12")) "true" else "false"
//        //        System.out.println(a);
//
//
//        val list = ArrayList<String>()
//
//        val listClass = list.javaClass
//        val m = listClass.getDeclaredMethod("add", Any::class.java)
//        m.isAccessible = true
//        m.invoke(list, User(1, 1))
//        println(list.toString())
//
//
//        val brick = Brick()
//        val aClass = brick.javaClass
//        val f = aClass.getDeclaredField("color")
//        f.isAccessible = true
//        f.set(brick, "#aaaa")
        //        System.out.print(brick.getColor());

        coroutine()
    }

    /**
     * 协程
     */
    fun coroutine() {
        launch(CommonPool) {
            delay(3000L, TimeUnit.MILLISECONDS)
            print("Hello")
        }

        print("world")
        Thread.sleep(5000L)

    }
}