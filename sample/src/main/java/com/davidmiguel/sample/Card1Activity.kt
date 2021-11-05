package com.davidmiguel.sample

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.davidmiguel.dragtoclose.DragListener
import com.davidmiguel.dragtoclose.DragToClose

/**
 * In this example the activity is closed when the card is dragged out.
 * Dragging events are logged.
 */
class Card1Activity : AppCompatActivity() {

    private val tag = "Card1Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card1)

        val dragToClose = findViewById<DragToClose>(R.id.drag_to_close)
        dragToClose.setDragListener(object : DragListener {
            override fun onStartDraggingView() {
                Log.d(tag, "onStartDraggingView()")
            }

            override fun onDragging(dragOffset: Float) {
                Log.d(tag, "onDragging(): $dragOffset")
            }

            override fun onViewCosed() {
                Log.d(tag, "onViewCosed()")
            }
        })
        val btn = findViewById<Button>(R.id.btn)
        btn.setOnClickListener { dragToClose.closeDraggableContainer() }
    }
}
