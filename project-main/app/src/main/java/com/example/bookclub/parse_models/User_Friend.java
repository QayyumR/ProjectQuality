package com.example.bookclub.parse_models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("User_Friend")
public class User_Friend extends ParseObject
{
    public static final String KEY_USER = "user";
    public static final String KEY_FRIEND = "friend";
    public static final String KEY_CREATED_AT = "createdAt";

    public void set_user(ParseUser u)
    {
        put(KEY_USER, u);
    }

    public ParseUser get_user(){ return getParseUser(KEY_USER); }

    public void set_friend(ParseUser f)
    {
        put(KEY_FRIEND, f);
    }

    public ParseUser get_friend()
    {
        return getParseUser(KEY_FRIEND);
    }
}
