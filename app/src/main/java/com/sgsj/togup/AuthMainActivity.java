package com.sgsj.togup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class AuthMainActivity extends AppCompatActivity {

    private Button loginButton, signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_auth_login);

        loginButton = findViewById(R.id.auth_main_login_button);
        signUpButton = findViewById(R.id.auth_main_signup_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginInAndSignUp();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginInAndSignUp();
            }
        });



    }

    private void loginInAndSignUp() {
        startActivity(new Intent(this,AuthLoginActivity.class));
    }
}
