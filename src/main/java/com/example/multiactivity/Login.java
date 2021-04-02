package com.example.multiactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    Button loginBtn;
    EditText loginUserName, loginPassword;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = (Button)findViewById(R.id.button3);
        loginUserName = (EditText) findViewById(R.id.editTextTextEmailAddress);
        loginPassword = (EditText) findViewById(R.id.editTextTextPassword);

        fAuth = FirebaseAuth.getInstance();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loginUser = loginUserName.getText().toString();
                String loginPass = loginPassword.getText().toString();
                SharedPreferences sharedPref = getSharedPreferences("userData", MODE_PRIVATE);
                String regUser = sharedPref.getString("regUser","");
                String regPass = sharedPref.getString("regPass","");

                if(!TextUtils.isEmpty(loginUser)||!TextUtils.isEmpty(loginPass) || regPass.length() >= 6){

                    fAuth.signInWithEmailAndPassword(loginUser,loginPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Login.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            }
                            else{
                                Toast.makeText(Login.this, "Username or Password is incorrect", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    /*if(loginUser.equals(regUser)&&loginPass.equals(regPass)){
                        Toast.makeText(getApplicationContext(),"Welcome To RareProb !!!",Toast.LENGTH_LONG).show();
                        Intent login = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(login);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Enter a valid Username or Password...",Toast.LENGTH_LONG).show();

                    }*/

                }
                else if (regPass.length() < 6){
                    Toast.makeText(getApplicationContext(),"Password length should be atleast 6 characters long...",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"!!!Username or Password cann't be Blank!!!\n Try Again...",Toast.LENGTH_LONG).show();
                    
                }


            }
        });
    }
}