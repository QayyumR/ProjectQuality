package com.example.bookclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity
{
    Button registerbtn, loginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        registerbtn = findViewById(R.id. registerButton);
        loginbtn = findViewById(R.id.loginButton);


        if(ParseUser.getCurrentUser() != null)
        {
            goTo_dashboardActivity();
        }

        registerbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                goTo_registerActivity();
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                goTo_loginActivity();
            }
        });
    }

    private void goTo_registerActivity()
    {
        Intent i = new Intent(MainActivity.this, RegistrationActivity.class);
        startActivity(i);
        finish();
    }

    private void goTo_loginActivity()
    {
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void goTo_dashboardActivity()
    {
        Intent i = new Intent(MainActivity.this, DashboardActivity.class);
        startActivity(i);
        finish();
    }
}