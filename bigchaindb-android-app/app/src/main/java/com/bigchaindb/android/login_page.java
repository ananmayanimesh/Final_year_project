package com.bigchaindb.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import com.sourcey.android.R;

public class login_page extends AppCompatActivity {
    Button signin, signup;
    EditText email, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        signin = findViewById(R.id.btn_login);
        signup = findViewById(R.id.btn_register);
        email = findViewById(R.id.mail_tx);
        pwd = findViewById(R.id.pass_tx);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().equals("admin") &&
                        pwd.getText().toString().equals("admin")) {
                    Toast.makeText(getApplicationContext(),
                            "Login Successful...", Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(login_page.this, TransactionActivity.class);
                    //startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                }
            }

        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_page.this, TransactionActivity.class);
                startActivity(intent);

            }
        });


    }

}

