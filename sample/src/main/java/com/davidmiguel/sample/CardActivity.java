package com.davidmiguel.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.davidmiguel.dragtoclose.DragListener;
import com.davidmiguel.dragtoclose.DragToClose;

public class CardActivity extends AppCompatActivity {
    
    private static final String TAG = CardActivity.class.getSimpleName();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        DragToClose outerLayout = (DragToClose) findViewById(R.id.drag_to_close);
        outerLayout.setDragListener(new DragListener() {
            @Override
            public void onStartDraggingView() {
                Log.d(TAG, "onStartDraggingView()");
            }

            @Override
            public void onViewCosed() {
                Log.d(TAG, "onViewCosed()");
            }
        });
    }
}
