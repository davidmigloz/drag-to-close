package com.davidmiguel.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.davidmiguel.dragtoclose.DragToClose;

/**
 * In this example the card can be dragged out card but the activity is not closed.
 * A label allows to reopen the card.
 */
public class Card2Activity extends AppCompatActivity {

    DragToClose dragToClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card2);
        dragToClose = findViewById(R.id.drag_to_close);
    }

    public void openCard(View view) {
        dragToClose.openDraggableContainer();
    }
}
