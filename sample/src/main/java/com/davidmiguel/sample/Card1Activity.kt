package com.davidmiguel.sample

import android.os.Bundle
import android.util.Log
import android.widget.Button

import com.davidmiguel.dragtoclose.DragListener
import com.davidmiguel.dragtoclose.DragToClose

import androidx.appcompat.app.AppCompatActivity

/**
 * In this example the activity is closed when the card is dragged out.
 * Dragging events are logged.
 */
class Card1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card1)

        val dragToClose = findViewById<DragToClose>(R.id.drag_to_close)
        dragToClose.setDragListener(object : DragListener {
            override fun onStartDraggingView() {
                Log.d(TAG, "onStartDraggingView()")
            }

            override fun onViewCosed() {
                Log.d(TAG, "onViewCosed()")
            }
        })
        val btn = findViewById<Button>(R.id.btn)
        btn.setOnClickListener { dragToClose.closeDraggableContainer() }
    }

    companion object {

        private val TAG = "Card1Activity"
    }
}
