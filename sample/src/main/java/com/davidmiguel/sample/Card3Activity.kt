package com.davidmiguel.sample

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

/**
 * In this example, DragToClose is used inside a fragment and the user can
 * close the activity by both dragging or clicking the arrow.
 */
class Card3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card3)
        if (savedInstanceState != null) return
        val card3Fragment = Card3Fragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.cardContainer, card3Fragment).commit()
    }
}
