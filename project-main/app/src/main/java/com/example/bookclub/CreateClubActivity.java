package com.example.bookclub;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookclub.parse_models.Club;
import com.example.bookclub.parse_models.Club_Members;
import com.example.bookclub.parse_models.Club_Message;
import com.google.android.material.textfield.TextInputEditText;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class CreateClubActivity extends AppCompatActivity
{
    private TextInputEditText et_create_club_name;
    private TextInputEditText et_create_club_description;
    private Button button_create_club;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_club);
        et_create_club_name = findViewById(R.id.et_create_club_name);
        et_create_club_description = findViewById(R.id.et_create_club_description);
        button_create_club = findViewById(R.id.button_create_club);

        button_create_club.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String club_name = et_create_club_name.getText().toString();
                String club_description = et_create_club_description.getText().toString();

                if (club_name.isEmpty())
                {
                    Toast.makeText(CreateClubActivity.this, "The club name field cannot be empty.", Toast.LENGTH_SHORT).show();
                }
                else if (club_description.isEmpty())
                {
                    Toast.makeText(CreateClubActivity.this, "The club description field cannot be empty.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    create_club(club_name, club_description);
                }
            }
        });
    }

    private void create_club_user(Club c)
    {
        Club_Members cm = new Club_Members();

        cm.set_club(c);
        cm.set_member(ParseUser.getCurrentUser());

        cm.saveInBackground(new SaveCallback()
        {
            @Override
            public void done(ParseException e)
            {
                if(e != null)
                {
                    Toast.makeText(CreateClubActivity.this, "Unable to add club member due to : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    finish();
                }
            }
        });
    }

    private void create_club(String n, String d)
    {
        Club c = new Club();

        c.set_name(n);
        c.set_description(d);

        c.saveInBackground(new SaveCallback()
        {
            @Override
            public void done(ParseException e)
            {
                if(e != null)
                {
                    Toast.makeText(CreateClubActivity.this, "Unable to create club due to : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    create_club_user(c);
                }
            }
        });
    }
}
