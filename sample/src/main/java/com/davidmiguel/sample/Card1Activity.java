package com.davidmiguel.sample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.davidmiguel.dragtoclose.DragListener;
import com.davidmiguel.dragtoclose.DragToClose;

import androidx.appcompat.app.AppCompatActivity;

/**
 * In this example the activity is closed when the card is dragged out.
 * Dragging events are logged.
 */
public class Card1Activity extends AppCompatActivity {

    private static final String TAG = Card1Activity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card1);

        final DragToClose dragToClose = findViewById(R.id.drag_to_close);
        dragToClose.setDragListener(new DragListener() {
            @Override
            public void onStartDraggingView() {
                Log.d(TAG, "onStartDraggingView()");
            }

            @Override
            public void onViewCosed() {
                Log.d(TAG, "onViewCosed()");
            }

            @Override
            public void onDragging(float dragOffset) {
                Log.d(TAG, String.format("onDragging(%.2f)", dragOffset));
            }
        });
        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dragToClose.closeDraggableContainer();
            }
        });
    }
}
