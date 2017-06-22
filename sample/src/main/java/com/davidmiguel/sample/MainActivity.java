package com.davidmiguel.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openCard1(View view) {
        Intent intent = new Intent(this, Card1Activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_up, 0);
    }

    public void openCard2(View view) {
        Intent intent = new Intent(this, Card2Activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_up, 0);
    }

    public void openCard3(View view) {
        Intent intent = new Intent(this, Card3Activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_up, 0);
    }
}
