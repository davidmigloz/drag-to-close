package com.davidmiguel.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.davidmiguel.dragtoclose.DragListener;
import com.davidmiguel.dragtoclose.DragToClose;

public class MainActivity extends AppCompatActivity {
    
    private static final String TAG = MainActivity.class.getSimpleName();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DragToClose outerLayout = (DragToClose) findViewById(R.id.outerLayout);
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
