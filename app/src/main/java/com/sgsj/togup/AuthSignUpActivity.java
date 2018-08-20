package com.sgsj.togup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AuthSignUpActivity extends AppCompatActivity {

    private EditText memail, mpassword, mphone,mconfirm,mname;
    private ImageView msignup;
    private ImageView mbackbtn;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mdatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_auth_sign_up);
        mAuth = FirebaseAuth.getInstance();
        memail = findViewById(R.id.auth_frag_login_email_edittext);
        mpassword = findViewById(R.id.auth_signup_password_edittext);
        memail = findViewById(R.id.auth_signup_email_edittext);
        mphone = findViewById(R.id.auth_signup_phone_edittext);
        mconfirm = findViewById(R.id.auth_signup_password_confirm_edittext);
        mname = findViewById(R.id.auth_signup_name_edittext);
        mpassword = findViewById(R.id.auth_signup_password_edittext);
        msignup = findViewById(R.id.sign_up);
        mbackbtn = findViewById(R.id.back_btn);
        msignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name,email,password,phone,confirm;
                name = mname.getText().toString().trim();
                email= memail.getText().toString().trim();
                password =mpassword.getText().toString().trim();
                confirm = mconfirm.getText().toString().trim();
                phone= mphone.getText().toString().trim();

                if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !phone.isEmpty() && !confirm.isEmpty()){
                    registerUser(name,email,password,phone,confirm);
                }else{
                    Toast.makeText(AuthSignUpActivity.this,"please fill all fields",Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    private void registerUser(final String name, final String email, String password, final String phone, String confirm) {
        if(password.equals(confirm)){
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                        String uid = current_user.getUid();
                        mdatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                        HashMap<String, String> usermap = new HashMap<>();
                        usermap.put("Name", name);
                        usermap.put("Email", email);
                        usermap.put("Phone", phone);
                        mdatabase.setValue(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task .isSuccessful()){
                                    startActivity(new Intent(AuthSignUpActivity.this,MainActivity.class));
                                    finish();


                                }
                            }
                        });


                    }
                }
            });

        }else{
            Toast.makeText(this,"password and confirm password should be same",Toast.LENGTH_SHORT).show();
        }

    }


}
