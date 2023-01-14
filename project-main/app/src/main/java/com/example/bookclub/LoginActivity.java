package com.example.bookclub;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class LoginActivity extends AppCompatActivity
{
    EditText emailEt, passwordEt;
    TextView notHaveAccntTv;
    Button loginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Login");

        // for the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //Firebase auth initialization
        //mAuth = FirebaseAuth.getInstance();

        emailEt = findViewById(R.id.et_username);
        passwordEt = findViewById(R.id.et_password);
        loginbtn = findViewById(R.id.button_login);
        notHaveAccntTv = findViewById(R.id.nothave_accountTv);

        loginbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //input email and password
                String emailIn = emailEt.getText().toString().trim();
                String passwordIn = passwordEt.getText().toString().trim();

                //validate
                /*if (!Patterns.EMAIL_ADDRESS.matcher(emailIn).matches())
                {
                    //set error
                    emailEt.setError("Invalid Email");
                    emailEt.setFocusable(true);
                }
                else{
                    //Register user
                    loginUser(emailIn,passwordIn);
                }*/
                loginUser(emailIn,passwordIn);
            }
        });
        //not have account textview
        notHaveAccntTv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });
    }

    // Parse login method
    private void loginUser(String username, String password)
    {
        ParseUser.logInInBackground(username, password, new LogInCallback()
        {
            @Override
            public void done(ParseUser user, ParseException e)
            {
                if(e != null)
                {
                    Toast.makeText(LoginActivity.this, "Login failed due to: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Login successful.", Toast.LENGTH_SHORT).show();
                    goTo_dashboardActivity();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void goTo_dashboardActivity()
    {
        Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity(i);
        finish();
    }
}