package com.example.bookclub;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookclub.parse_models.Club;
import com.example.bookclub.parse_models.Club_Message;
import com.example.bookclub.parse_models.User_Friend;
import com.example.bookclub.parse_models.User_Message;
import com.example.bookclub.rv_adapters.ClubMessageAdapter;
import com.example.bookclub.rv_adapters.MessageAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class ClubActivity extends AppCompatActivity
{
    private TextInputEditText et_message_club;
    private Button button_club_send;
    private RecyclerView rv_club_messages;
    private TextView tv_club_name_big;
    private TextView tv_club_description_big;
    protected ClubMessageAdapter rv_club_messages_adapter;
    private ParseUser user;
    private Club club;
    private List<Club_Message> club_messages;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_club);
        et_message_club = findViewById(R.id.et_message_club);
        button_club_send = findViewById(R.id.button_club_send);
        rv_club_messages = findViewById(R.id.rv_club_messages);
        club_messages = new ArrayList<Club_Message>();
        rv_club_messages_adapter = new ClubMessageAdapter(ClubActivity.this, club_messages);
        tv_club_name_big = findViewById(R.id.tv_club_name_big);
        tv_club_description_big = findViewById(R.id.tv_club_description_big);

        rv_club_messages.setAdapter(rv_club_messages_adapter);
        rv_club_messages.setLayoutManager(new LinearLayoutManager(ClubActivity.this));

        String club_name = getIntent().getStringExtra("club_data");

        user = ParseUser.getCurrentUser();
        get_club(club_name);

        button_club_send.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String message_content = et_message_club.getText().toString();

                if (!message_content.isEmpty())
                {
                    et_message_club.setText("");
                    create_club_message(message_content);
                }
            }
        });
    }

    private void create_club_message(String m)
    {
        Club_Message cm = new Club_Message();

        cm.set_message(m);
        cm.set_sender(user);
        cm.set_club(club);

        cm.saveInBackground(new SaveCallback()
        {
            @Override
            public void done(ParseException e)
            {
                if(e != null)
                {
                    Toast.makeText(ClubActivity.this, "Unable to send message due to : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(ClubActivity.this, "Message sent.", Toast.LENGTH_SHORT).show();
                    get_club_messages();
                }
            }
        });
    }

    private void clear_club_messages()
    {
        club_messages.clear();
        rv_club_messages_adapter.notifyDataSetChanged();
    }

    private void populate_club_messages(List<Club_Message> cm)
    {
        club_messages.addAll(cm);
        rv_club_messages_adapter.notifyDataSetChanged();
    }

    private void get_club_messages()
    {
        ParseQuery<Club_Message> query = ParseQuery.getQuery(Club_Message.class);
        query.include(Club_Message.KEY_SENDER);
        query.whereEqualTo(Club_Message.KEY_CLUB, club);
        query.addAscendingOrder(User_Friend.KEY_CREATED_AT);

        query.findInBackground(new FindCallback<Club_Message>()
        {
            @Override
            public void done(List<Club_Message> objects, ParseException e)
            {
                if(e != null)
                {
                    Toast.makeText(ClubActivity.this, "Unable to load message data.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    clear_club_messages();
                    populate_club_messages(objects);
                }
            }
        });
    }

    private void get_club(String c)
    {
        ParseQuery<Club> query = ParseQuery.getQuery(Club.class);
        query.whereEqualTo(Club.KEY_NAME, c);

        query.findInBackground(new FindCallback<Club>()
        {
            @Override
            public void done(List<Club> objects, ParseException e)
            {
                if(e != null)
                {
                    Toast.makeText(ClubActivity.this, "Unable to club data due to : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(ClubActivity.this, "Club loading successful.", Toast.LENGTH_SHORT).show();
                    club = (objects.get(0));

                    tv_club_name_big.setText(objects.get(0).get_name());
                    tv_club_description_big.setText(objects.get(0).get_description());
                    get_club_messages();
                }
            }
        });
    }
}