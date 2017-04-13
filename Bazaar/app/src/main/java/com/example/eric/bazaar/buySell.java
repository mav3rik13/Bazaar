package com.example.eric.bazaar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class buySell extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_sell);
    }

    public void buy(View view){

            Intent intent = new Intent(this, viewItems.class);
            intent.putExtra("message", "Buy");
            startActivity(intent);

    }

    public void sell(View view){

        Intent intent = new Intent(this, viewItems.class);
        intent.putExtra("message", "Sell");
        startActivity(intent);

    }

    public void all(View view){

        Intent intent = new Intent(this, viewItems.class);
        intent.putExtra("message", "All");
        startActivity(intent);

    }
}
