package com.davidmiguel.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * In this example, DragToClose is used inside a fragment and the user can
 * close the activity by both dragging or clicking the arrow.
 */
public class Card3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card3);
        if (savedInstanceState != null) {
            return;
        }
        Card3Fragment card3Fragment = Card3Fragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.cardContainer, card3Fragment).commit();
    }
}
