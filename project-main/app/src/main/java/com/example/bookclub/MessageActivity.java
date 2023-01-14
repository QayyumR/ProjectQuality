package com.example.bookclub;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookclub.parse_models.User_Extra;
import com.example.bookclub.parse_models.User_Friend;
import com.example.bookclub.parse_models.User_Message;
import com.example.bookclub.rv_adapters.FriendAdapter;
import com.example.bookclub.rv_adapters.MessageAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity
{
    private TextInputEditText et_message;
    private Button button_send;
    private RecyclerView rv_messages;
    protected MessageAdapter rv_messages_adapter;
    private ParseUser friend;
    private ParseUser user;
    private List<User_Message> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_message);
        et_message = findViewById(R.id.et_message);
        button_send = findViewById(R.id.button_send);
        rv_messages = findViewById(R.id.rv_messages);
        messages = new ArrayList<User_Message>();
        rv_messages_adapter = new MessageAdapter(MessageActivity.this, messages);

        rv_messages.setAdapter(rv_messages_adapter);
        rv_messages.setLayoutManager(new LinearLayoutManager(MessageActivity.this));

        String friend_username = getIntent().getStringExtra("friend_data");

        user = ParseUser.getCurrentUser();
        get_friend(friend_username);

        button_send.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String message_content = et_message.getText().toString();

                if (!message_content.isEmpty())
                {
                    et_message.setText("");
                    create_message(message_content);
                }
            }
        });
    }

    private void create_message(String m)
    {
        User_Message um = new User_Message();

        um.set_content(m);
        um.set_reciever(friend);
        um.set_sender(user);

        um.saveInBackground(new SaveCallback()
        {
            @Override
            public void done(ParseException e)
            {
                if(e != null)
                {
                    Toast.makeText(MessageActivity.this, "Unable to send message due to : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MessageActivity.this, "Message sent.", Toast.LENGTH_SHORT).show();
                    get_messages();
                }
            }
        });
    }

    private void clear_messages()
    {
        messages.clear();
        rv_messages_adapter.notifyDataSetChanged();
    }

    private void populate_messages(List<User_Message> m)
    {
        messages.addAll(m);
        rv_messages_adapter.notifyDataSetChanged();
    }

    private void get_messages()
    {
        List<ParseUser> sender_reciever = new ArrayList<ParseUser>();
        sender_reciever.add(user);
        sender_reciever.add(friend);

        ParseQuery<User_Message> query = ParseQuery.getQuery(User_Message.class);
        query.include(User_Message.KEY_SENDER);
        query.include(User_Message.KEY_RECIEVER);
        query.whereContainedIn(User_Message.KEY_SENDER, sender_reciever);
        query.whereContainedIn(User_Message.KEY_RECIEVER, sender_reciever);
        query.addAscendingOrder(User_Friend.KEY_CREATED_AT);

        query.findInBackground(new FindCallback<User_Message>()
        {
            @Override
            public void done(List<User_Message> objects, ParseException e)
            {
                if(e != null)
                {
                    Toast.makeText(MessageActivity.this, "Unable to load message data.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    clear_messages();
                    populate_messages(objects);
                }
            }
        });
    }

    private void get_friend(String f)
    {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", f);

        query.findInBackground(new FindCallback<ParseUser>()
        {
            @Override
            public void done(List<ParseUser> objects, ParseException e)
            {
                if(e != null)
                {
                    Toast.makeText(MessageActivity.this, "Unable to load friend data.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    friend = objects.get(0);
                    get_messages();
                }
            }
        });
    }
}