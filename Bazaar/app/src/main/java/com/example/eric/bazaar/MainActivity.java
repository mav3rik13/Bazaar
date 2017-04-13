package com.example.eric.bazaar;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {
    private final String USER_AGENT = "Mozilla/5.0";
    EditText edtuserid,edtpass;
    Button btnlogin;
    ProgressBar pbbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        edtuserid = (EditText) findViewById(R.id.userName);
        edtpass = (EditText) findViewById(R.id.password);
        btnlogin = (Button) findViewById(R.id.button3);
        pbbar = (ProgressBar) findViewById(R.id.pbbar);
        pbbar.setVisibility(View.GONE);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void login(View view) throws IOException, ParserConfigurationException {
        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        String s=edtuserid.getText().toString();
        String t=edtpass.getText().toString();
        String url = "http://cis-linux2.temple.edu/~tud17750/bazaar/login.php?inputName="+s+"&inputPassword="+t;
        url=url.replaceAll(" ", "");

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();


        StringBuilder xmlStringBuilder = new StringBuilder();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        //System.out.println("Got here");
        while ((inputLine = in.readLine()) != null) {
            xmlStringBuilder.append(inputLine);
        }

        in.close();
        ByteArrayInputStream input =  new ByteArrayInputStream(
                xmlStringBuilder.toString().getBytes("UTF-8"));
        System.out.println("Got here");

        if (!Objects.equals("Query failed",xmlStringBuilder.toString())) {

            Document doc = null;//Crashes here
            try {
                doc = builder.parse(input);
            } catch (SAXException e) {
                e.printStackTrace();
            }

            NodeList name;
            name = doc.getElementsByTagName("user");
            System.out.println(name.item(0).getFirstChild().getTextContent());

            String par = name.item(0).getFirstChild().getTextContent();
            String uName;
            String ID;
            String Email;

            par = par.replaceAll("\t", "");

            String[] split = par.split(" ");

            ((MyApplication) this.getApplication()).setID(split[0]);
            ((MyApplication) this.getApplication()).setEmail(split[1]);
            ((MyApplication) this.getApplication()).setuName(split[2]);
            ((MyApplication) this.getApplication()).setRating(split[3]);

            Intent intent = new Intent(this, menu.class);
            startActivity(intent);
        }
        else{
            Context context = getApplicationContext();
            CharSequence text = "Incorrect Password or userName";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        }
    }

    public void register(View view){
        Intent intent = new Intent(this, createProfile.class);
        startActivity(intent);
    }
}
