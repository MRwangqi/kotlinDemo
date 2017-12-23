package com.codelang.kotlin;

import com.codelang.kotlin.model.Brick;
import com.codelang.kotlin.model.User;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);

        String hex = "#" + Integer.toHexString((int) (-16777216 * Math.random()));
//        System.out.print("#" + hex);


        String a = Long.valueOf("12") == Long.valueOf("12") ? "true" : "false";
//        System.out.println(a);


        List<String> list = new ArrayList<>();

        Class listClass = list.getClass();
        Method m = listClass.getDeclaredMethod("add", Object.class);
        m.setAccessible(true);
        m.invoke(list, new User(1, 1));
        System.out.println(list.toString());


        Brick brick = new Brick();
        Class aClass = brick.getClass();
        Field f = aClass.getDeclaredField("color");
        f.setAccessible(true);
        f.set(brick, "#aaaa");
//        System.out.print(brick.getColor());
    }
}