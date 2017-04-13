package com.example.eric.bazaar;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class sendMessage extends AppCompatActivity {
    TextView Sender;
    TextView Mess;
    TextView Item;
    EditText text;
    private final String USER_AGENT = "Mozilla/5.0";
    String[] split;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("message");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Sender = (TextView) findViewById(R.id.sender);
        Mess = (TextView) findViewById(R.id.mess);
        Item = (TextView) findViewById(R.id.Item);
        text = (EditText) findViewById(R.id.text);


        split=message.split(";");

        Sender.setText(split[1]);
        Mess.setText(split[2]);
        Item.setText(split[3]);
    }

    public void send(View view) throws ParserConfigurationException, IOException {
        String message=text.getText().toString();
        message=message.replace(' ','_');

        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();


        String user=((MyApplication) this.getApplication()).getuName();
        String rating=((MyApplication) this.getApplication()).getRating();

        String url = "http://cis-linux2.temple.edu/~tud17750/bazaar/sendMessage.php?text="+message+"&item="+split[3]+"&receiver="+split[1]+"&sender="+user+"&rating="+rating;
        url=url.replace(" ","");

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();

        System.out.println("Response Code : " + responseCode);

        Intent intent = new Intent(this, menu.class);
        startActivity(intent);

    }
}
