package com.example.bookclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.bookclub.main_fragments.Clubs_Fragment;
import com.example.bookclub.main_fragments.Friend_Fragment;
import com.example.bookclub.main_fragments.Profile_Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity
{
    public static final String TAG = "MainActivity";

    private final FragmentManager fm = getSupportFragmentManager();
    private final Fragment clubs = new Clubs_Fragment();
    private final Fragment friend = new Friend_Fragment();
    private final Fragment profile = new Profile_Fragment();

    private BottomNavigationView bottom_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);

        bottom_menu = findViewById(R.id.MAIN_NAVIGATION);
        final int friends_action = R.id.action_friends;
        final int group_action = R.id.action_group;
        final int profile_action = R.id.action_profile;

        bottom_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                Fragment fragment;

                switch (item.getItemId())
                {
                    case friends_action:
                        fragment = friend;
                        break;
                    case group_action:
                        fragment = clubs;
                        break;
                    case profile_action:
                        fragment = profile;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected menu item: " + item.getItemId());
                }

                fm.beginTransaction().replace(R.id.MAIN_FRAME_FRAGMENT, fragment).commit();

                return true;
            }
        });

        // Set default selection
        bottom_menu.setSelectedItemId(R.id.action_profile);
    }
}