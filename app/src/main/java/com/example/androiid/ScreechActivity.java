package com.example.androiid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class ScreechActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screech);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent mainIntent = new Intent(ScreechActivity.this, LoginActivity.class);
                ScreechActivity.this.startActivity(mainIntent);
                finish();
            }
        }, 3000);

    }
}
