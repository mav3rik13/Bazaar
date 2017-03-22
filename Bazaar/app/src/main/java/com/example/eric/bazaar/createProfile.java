package com.example.eric.bazaar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.sql.*;

public class createProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
    }

    public void create(){
        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection con=DriverManager.getConnection("jdbc:mysql://cis-linux2.temple.edu/SP15_2305_tud17750","tud17750","geengiel");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("INSERT INTO User (Email, Password, userName)" +
                    "VALUES( , , );");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
