package com.example.eric.bazaar;

import android.content.Intent;
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

public class itemPage extends AppCompatActivity {
    TextView Name;
    TextView Desc;
    TextView Price;
    TextView Seller;
    EditText mess;
    private final String USER_AGENT = "Mozilla/5.0";
    String[] split;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_page);

        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("message");

        Name = (TextView) findViewById(R.id.Name);
        Desc = (TextView) findViewById(R.id.Description);
        Price = (TextView) findViewById(R.id.Price);
        mess = (EditText) findViewById(R.id.Message);
        Seller= (TextView) findViewById(R.id.seller);


        split=message.split(";");

        Name.setText(split[1]);
        Desc.setText(split[2]);
        Price.setText("$"+split[3]);
        Seller.setText(split[4]);
    }

    public void sendMessage(View view) throws ParserConfigurationException, IOException {
        String message=mess.getText().toString();
        message=message.replace(' ','_');

        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();


        String user=((MyApplication) this.getApplication()).getuName();
        String rating =((MyApplication) this.getApplication()).getRating();

        String rec=split[4].replace(":", "");
        rec=rec.replace(".", "");


        String url = "http://cis-linux2.temple.edu/~tud17750/bazaar/sendMessage.php?text="+message+"&item="+split[1]+"&receiver="+rec+"&sender="+user+"&rating="+rating;

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
