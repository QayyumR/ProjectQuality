package com.example.bookclub.main_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookclub.Base_Fragment;
import com.example.bookclub.ClubActivity;
import com.example.bookclub.CreateClubActivity;
import com.example.bookclub.R;
import com.example.bookclub.parse_models.Club;
import com.example.bookclub.parse_models.Club_Members;
import com.example.bookclub.parse_models.User_Friend;
import com.example.bookclub.rv_adapters.ClubAdapter;
import com.example.bookclub.rv_adapters.FriendAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class Clubs_Fragment extends Base_Fragment
{
    private Button button_create_club;
    private RecyclerView rv_clubs;
    protected ClubAdapter rv_clubs_adapter;
    private List<Club> clubs;

    @Override
    protected View inflate_view(LayoutInflater i, ViewGroup c)
    {
        return i.inflate(R.layout.fragment_clubs, c, false);
    }

    @Override
    protected void init_components(View v)
    {
        button_create_club = v.findViewById(R.id.button_create_club);
        rv_clubs = v.findViewById(R.id.rv_clubs);

        button_create_club.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                goTo_createClubActivity();
                getClubs();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        init_components(view);
        clubs = new ArrayList<Club>();
        rv_clubs_adapter = new ClubAdapter(getContext(), clubs);

        rv_clubs.setAdapter(rv_clubs_adapter);
        rv_clubs.setLayoutManager(new LinearLayoutManager(getContext()));

        getClubs();
    }

    private void clear_clubs()
    {
        clubs.clear();
        rv_clubs_adapter.notifyDataSetChanged();
    }

    private void populate_clubs(List<Club> c)
    {
        clubs.addAll(c);
        rv_clubs_adapter.notifyDataSetChanged();
    }

    private void getClubs()
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
                    clear_clubs();

                    List<Club> club_list = new ArrayList<Club>();

                    for(int x = 0; x < objects.size(); x++)
                    {
                        club_list.add(objects.get(x).get_club());
                    }

                    populate_clubs(club_list);
                }
            }
        });
    }

    protected void goTo_createClubActivity()
    {
        Intent i = new Intent(getContext(), CreateClubActivity.class);
        getContext().startActivity(i);
    }
}
