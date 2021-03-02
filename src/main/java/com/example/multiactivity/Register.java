package com.example.multiactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    EditText regUserName, regPassword;
    private Button signupBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        signupBtn = (Button)findViewById(R.id.button6);
        regUserName = (EditText) findViewById(R.id.editTextTextEmailAddress2);
        regPassword = (EditText) findViewById(R.id.editTextTextPassword2);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String regUser = regUserName.getText().toString();
                String regPass = regPassword.getText().toString();
                if(TextUtils.isEmpty(regUser)||TextUtils.isEmpty(regPass)){
                    Toast.makeText(getApplicationContext(),"UserName or Password Cant be blank...",Toast.LENGTH_LONG).show();

                }
                else{
                    SharedPreferences sharedPref = getSharedPreferences("userData",MODE_PRIVATE);
                    SharedPreferences.Editor edit = sharedPref.edit();

                    edit.putString("regUser", regUser);
                    edit.putString("regPass",regPass);
                    edit.commit();
                    Toast.makeText(getApplicationContext(),"Registration Successful \n Login Yourself :)",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}