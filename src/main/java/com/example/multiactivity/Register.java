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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {
    EditText regUserName, regPassword, userName;
    private Button signupBtn;
    FirebaseAuth fAuth;
    TextView forgetPassword;
    TextView forgetPas;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        signupBtn = (Button)findViewById(R.id.button6);
        regUserName = (EditText) findViewById(R.id.editTextTextEmailAddress2);
        regPassword = (EditText) findViewById(R.id.editTextTextPassword2);
        userName = (EditText) findViewById(R.id.editTextTextPersonName);
        fAuth = FirebaseAuth.getInstance();
        forgetPassword = findViewById(R.id.tv_forget_pass);
        forgetPas = findViewById(R.id.editTextForgetPassword2);

        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        /*if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }*/

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String regUser = regUserName.getText().toString().trim();
                String regPass = regPassword.getText().toString().trim();
                String uName = userName.getText().toString().trim();
                if(TextUtils.isEmpty(regUser)||TextUtils.isEmpty(regPass)){
                    Toast.makeText(getApplicationContext(),"UserName or Password Cant be blank...",Toast.LENGTH_LONG).show();

                }
                else if (regPass.length() < 6){
                    Toast.makeText(getApplicationContext(),"Password length should be atleast 6 characters long...",Toast.LENGTH_LONG).show();
                }
                else{
                    SharedPreferences sharedPref = getSharedPreferences("userData",MODE_PRIVATE);
                    SharedPreferences.Editor edit = sharedPref.edit();

                    edit.putString("uName", uName);
                    edit.putString("regUser", regUser);
                    edit.putString("regPass", regPass);
                    edit.commit();

                    fAuth.createUserWithEmailAndPassword(regUser, regPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Registration Successful \n Login Yourself :)",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                String userId = mDatabase.push().getKey();

                                // creating user object
                                User user = new User(uName,regUser,regPass);

                                // pushing user to 'users' node using the userId
                                mDatabase.child(userId).setValue(user);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Error ! "+task.getException(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            }
        });
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"forget Password",Toast.LENGTH_SHORT).show();
                getData();
            }
        });

    }

    private void getData() {
        // calling add value event listener method
        // for getting the values from database.
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String value = snapshot.getValue(String.class);

                forgetPas.setVisibility(View.VISIBLE);
                forgetPas.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(getApplicationContext(), "Fail to get data."+error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}