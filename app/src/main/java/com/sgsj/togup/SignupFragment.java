package com.sgsj.togup;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends android.support.v4.app.Fragment {

    private FloatingActionButton signUpFab;

    private EditText signUpName, signUpEmail;


    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_signup_fragment, container, false);

        signUpName = view.findViewById(R.id.auth_frag_signup_name_edittext);
        signUpEmail = view.findViewById(R.id.auth_frag_signup_email_edittext);

        signUpFab = view.findViewById(R.id.auth_frag_signup_fab);

        signUpFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AuthSignUpActivity.class);
                i.putExtra("NAME",signUpName.getText().toString().trim());
                i.putExtra("EMAIL", signUpEmail.getText().toString().trim());
                startActivity(i);
            }
        });



        return view;
    }

}
