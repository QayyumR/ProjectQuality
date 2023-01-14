package com.example.bookclub.main_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookclub.Base_Fragment;
import com.example.bookclub.MainActivity;
import com.example.bookclub.R;
import com.example.bookclub.RegistrationActivity;
import com.example.bookclub.parse_models.User_Extra;
import com.example.bookclub.parse_models.User_Friend;
import com.example.bookclub.rv_adapters.FriendAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class Friend_Fragment extends Base_Fragment
{
    private TextInputEditText et_add_friend;
    private Button button_add_friend;
    private RecyclerView rv_friends;
    protected FriendAdapter rv_friends_adapter;
    private List<User_Friend> friends;

    @Override
    protected View inflate_view(LayoutInflater i, ViewGroup c)
    {
        return i.inflate(R.layout.fragment_friend, c, false);
    }

    @Override
    protected void init_components(View v)
    {
        et_add_friend = v.findViewById(R.id.et_add_friend);
        button_add_friend = v.findViewById(R.id.button_add_friend);
        rv_friends = v.findViewById(R.id.rv_friends);

        button_add_friend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String new_friend_name = et_add_friend.getText().toString();

                if (!new_friend_name.isEmpty())
                {
                    find_user_new_friend(new_friend_name);
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        init_components(view);
        friends = new ArrayList<User_Friend>();
        rv_friends_adapter = new FriendAdapter(getContext(), friends);

        rv_friends.setAdapter(rv_friends_adapter);
        rv_friends.setLayoutManager(new LinearLayoutManager(getContext()));

        getFriends();
    }

    private void clear_friends()
    {
        friends.clear();
        rv_friends_adapter.notifyDataSetChanged();
    }

    private void populate_friends(List<User_Friend> f)
    {
        friends.addAll(f);
        rv_friends_adapter.notifyDataSetChanged();
    }

    private void getFriends()
    {
        ParseQuery<User_Friend> query = ParseQuery.getQuery(User_Friend.class);
        query.include(User_Friend.KEY_USER);
        query.include(User_Friend.KEY_FRIEND);
        query.addDescendingOrder(User_Friend.KEY_CREATED_AT);
        query.whereEqualTo(User_Friend.KEY_USER, ParseUser.getCurrentUser());

        query.findInBackground(new FindCallback<User_Friend>()
        {
            @Override
            public void done(List<User_Friend> objects, ParseException e)
            {
                if(e != null)
                {
                    Toast.makeText(getContext(), "Unable to load friends due to : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Friend loading successful.", Toast.LENGTH_SHORT).show();

                    clear_friends();
                    populate_friends(objects);
                }
            }
        });
    }

    private void add_friend(ParseUser f)
    {
        User_Friend uf = new User_Friend();

        uf.set_user(ParseUser.getCurrentUser());
        uf.set_friend(f);

        uf.saveInBackground(new SaveCallback()
        {
            @Override
            public void done(ParseException e)
            {
                if(e != null)
                {
                    Toast.makeText(getContext(), "Unable to add new friend due to : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Friend added successfully.", Toast.LENGTH_SHORT).show();
                    getFriends();
                }
            }
        });
    }

    private void find_user_new_friend(String username)
    {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", username);

        query.findInBackground(new FindCallback<ParseUser>()
        {
            @Override
            public void done(List<ParseUser> objects, ParseException e)
            {
                if(e != null)
                {
                    Toast.makeText(getContext(), "No users with that username found", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (objects.size() == 1)
                    {
                        String new_friend_username = objects.get(0).getUsername();

                        if (new_friend_username.equals(ParseUser.getCurrentUser().getUsername()))
                        {
                            Toast.makeText(getContext(), "You cannot add yourself to your own friends list.", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            boolean match_found = false;

                            for (int x = 0; x < friends.size(); x++)
                            {
                                User_Friend f = friends.get(x);

                                if (f.get_friend().getUsername().equals(new_friend_username))
                                {
                                    Toast.makeText(getContext(), "You are already friends with " + new_friend_username + ".", Toast.LENGTH_SHORT).show();
                                    match_found = true;
                                    break;
                                }
                            }

                            if (!match_found)
                            {
                                add_friend(objects.get(0));
                            }
                        }
                    }
                    else
                    {
                        Toast.makeText(getContext(), "No users with that username found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
