package com.example.eric.bazaar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view){

        Intent intent = new Intent(this, menu.class);
        startActivity(intent);
    }

    public void register(View view){
        Intent intent = new Intent(this, createProfile.class);
        startActivity(intent);
    }
}
