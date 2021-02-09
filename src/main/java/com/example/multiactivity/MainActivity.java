package com.example.multiactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button registerPage,loginPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerPage = (Button)findViewById(R.id.button);
        loginPage = (Button)findViewById(R.id.button2);

        registerPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regPage = new Intent(getApplicationContext(), Register.class);
                startActivity(regPage);
            }
        });
        loginPage.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                Intent logPage = new Intent(getApplicationContext(), Login.class);
                startActivity(logPage);
            }
        });
    }


}