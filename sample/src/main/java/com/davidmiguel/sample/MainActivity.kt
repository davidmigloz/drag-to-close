package com.davidmiguel.sample

import android.content.Intent
import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @Suppress("UNUSED_PARAMETER")
    fun openCard1(view: View) {
        val intent = Intent(this, Card1Activity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_up, 0)
    }

    @Suppress("UNUSED_PARAMETER")
    fun openCard2(view: View) {
        val intent = Intent(this, Card2Activity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_up, 0)
    }

    @Suppress("UNUSED_PARAMETER")
    fun openCard3(view: View) {
        val intent = Intent(this, Card3Activity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_up, 0)
    }
}
