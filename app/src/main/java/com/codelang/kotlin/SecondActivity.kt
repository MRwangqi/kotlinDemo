package com.codelang.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.codelang.kotlin.model.User
import com.codelang.kotlin.sql.database
import kotlinx.android.synthetic.main.activity_second.*
import org.jetbrains.anko.sdk25.coroutines.onClick


class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
//        val id = intent.getStringExtra("id")
//        second_title.text = id

//        val u = User(1, 2)
//        second_title.append(u.toString())

//        pie1.setPieData(arrayListOf(1f,10f,15f,9f,15f))
//        pie2.setPieData(arrayListOf(3f,8f,15f,7f,9f))
//        pie3.setPieData(arrayListOf(9f,3f,7f,3f,4f,2f,1f))

//        start.setOnClickListener {
//            pie3.anim()
//        }
    }
}
