package com.example.bookclub.parse_models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("User_Extra")
public class User_Extra extends ParseObject
{
    public static final String KEY_DESCRIPTION = "profile_description";
    public static final String KEY_IMAGE = "profile_picture";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_AT = "createdAt";

    public void set_profile_description(String d)
    {
        put(KEY_DESCRIPTION, d);
    }

    public String get_profile_description()
    {
        return getString(KEY_DESCRIPTION);
    }

    public void set_profile_picture(ParseFile i)
    {
        put(KEY_IMAGE, i);
    }

    public ParseFile get_profile_picture()
    {
        return getParseFile(KEY_IMAGE);
    }

    public void set_user(ParseUser u)
    {
        put(KEY_USER, u);
    }

    public ParseUser get_user(){ return getParseUser(KEY_USER); }
}
