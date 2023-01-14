package com.example.bookclub;

import android.app.Application;

import com.example.bookclub.parse_models.Club;
import com.example.bookclub.parse_models.Club_Members;
import com.example.bookclub.parse_models.Club_Message;
import com.example.bookclub.parse_models.User_Extra;
import com.example.bookclub.parse_models.User_Friend;
import com.example.bookclub.parse_models.User_Message;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        ParseObject.registerSubclass(User_Extra.class);
        ParseObject.registerSubclass(User_Friend.class);
        ParseObject.registerSubclass(User_Message.class);
        ParseObject.registerSubclass(Club.class);
        ParseObject.registerSubclass(Club_Members.class);
        ParseObject.registerSubclass(Club_Message.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("588yQ1MIURTAJq21oXOixyg6FAUDHNlpuyYlolJY")
                .clientKey("9u9cAbVSyJ3tiAUBp96T9kSKgOn0VWSSKnOihxgM")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
