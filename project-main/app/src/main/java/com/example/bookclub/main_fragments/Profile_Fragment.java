package com.example.bookclub.main_fragments;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookclub.Base_Fragment;
import com.example.bookclub.MainActivity;
import com.example.bookclub.R;
import com.example.bookclub.parse_models.Club;
import com.example.bookclub.parse_models.Club_Members;
import com.example.bookclub.parse_models.Club_Message;
import com.example.bookclub.parse_models.User_Extra;
import com.example.bookclub.parse_models.User_Friend;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class Profile_Fragment extends Base_Fragment
{
    private ImageView profilePic;
    private TextView profileName;
    private TextView clubs_num;
    private TextInputEditText description_text;
    private Button action_logout;
    private User_Extra ue;
    private Button save_button;

    @Override
    protected View inflate_view(LayoutInflater i, ViewGroup c)
    {
        return i.inflate(R.layout.fragment_profile, c, false);
    }

    @Override
    protected void init_components(View v)
    {
        get_user_extra();

        profilePic = v.findViewById(R.id.profilePic);
        profileName = v.findViewById(R.id.profileName);
        clubs_num = v.findViewById(R.id.clubs_num);
        description_text = v.findViewById(R.id.description_text);
        action_logout = v.findViewById(R.id.action_logout);
        save_button = v.findViewById(R.id.button_save_description);

        profileName.setText(ParseUser.getCurrentUser().getUsername());
        get_num_clubs();

        action_logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ParseUser.logOut();
                goTo_mainActivity();
            }
        });

        save_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                update_user_extra();
            }
        });
    }

    public void goTo_mainActivity()
    {
        Intent i = new Intent(getContext(), MainActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    private void set_user_extra_fields()
    {
        if (ue == null)
        {
            description_text.setText("Unable to load profile description");
        }
        else
        {
            description_text.setText(ue.get_profile_description());
        }
    }

    private void get_user_extra()
    {
        ParseQuery<User_Extra> query = ParseQuery.getQuery(User_Extra.class);
        query.whereEqualTo(User_Extra.KEY_USER, ParseUser.getCurrentUser());

        query.findInBackground(new FindCallback<User_Extra>()
        {
            @Override
            public void done(List<User_Extra> objects, ParseException e)
            {
                if(e != null)
                {
                    Toast.makeText(getContext(), "Unable to load user_extra due to : " + e.getMessage(), Toast.LENGTH_SHORT).show();

                    ue = null;
                }
                else
                {
                    Toast.makeText(getContext(), "user_extra loaded successfully.", Toast.LENGTH_SHORT).show();

                    ue = objects.get(0);
                }

                set_user_extra_fields();
            }
        });
    }

    private void update_user_extra()
    {
        String description = description_text.getText().toString();

        ue.set_profile_description(description);

        ue.saveInBackground(new SaveCallback()
        {
            @Override
            public void done(ParseException e)
            {
                if(e != null)
                {
                    Toast.makeText(getContext(), "Unable to save User_Extra due to : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "User_Extra saved successfully.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void get_num_clubs()
    {
        ParseQuery<Club_Members> query = ParseQuery.getQuery(Club_Members.class);
        query.include(Club_Members.KEY_CLUB);
        query.addDescendingOrder(User_Friend.KEY_CREATED_AT);
        query.whereEqualTo(Club_Members.KEY_MEMBER, ParseUser.getCurrentUser());

        query.findInBackground(new FindCallback<Club_Members>()
        {
            @Override
            public void done(List<Club_Members> objects, ParseException e)
            {
                if(e != null)
                {
                    Toast.makeText(getContext(), "Unable to load clubs due to : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    List<Club> club_list = new ArrayList<Club>();

                    for(int x = 0; x < objects.size(); x++)
                    {
                        club_list.add(objects.get(0).get_club());
                    }

                    int num_clubs = club_list.size();

                    clubs_num.setText(String.valueOf(num_clubs));
                }
            }
        });
    }
}
