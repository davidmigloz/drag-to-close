package com.davidmiguel.sample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.davidmiguel.dragtoclose.DragToClose

/**
 * In this example the card can be dragged out card but the activity is not closed.
 * A label allows to reopen the card.
 */
class Card2Activity : AppCompatActivity() {

    private lateinit var dragToClose: DragToClose

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card2)
        dragToClose = findViewById(R.id.drag_to_close)
    }

    @Suppress("UNUSED_PARAMETER")
    fun openCard(view: View) {
        dragToClose.openDraggableContainer()
    }
}
