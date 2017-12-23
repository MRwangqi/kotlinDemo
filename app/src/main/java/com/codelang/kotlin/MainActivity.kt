package com.codelang.kotlin

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import com.codelang.kotlin.view.MainView
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity(), AnkoLogger {


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MainView().setContentView(this)


    }


    /**
     * Activity需要继承AnkoLogger
     */
    private fun debugLog() {


        info("hahah")
        debug("hahah")
        error("hahha")
        warn("hahha")
    }


}

