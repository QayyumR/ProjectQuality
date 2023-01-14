package com.example.bookclub.parse_models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("User_Message")
public class User_Message extends ParseObject
{
    public static final String KEY_CONTENT = "content";
    public static final String KEY_SENDER = "sender";
    public static final String KEY_RECIEVER = "reciever";
    public static final String KEY_CREATED_AT = "createdAt";

    public void set_content(String c)
    {
        put(KEY_CONTENT, c);
    }

    public String get_content()
    {
        return getString(KEY_CONTENT);
    }

    public void set_sender(ParseUser s) { put(KEY_SENDER, s); }

    public ParseUser get_sender()
    {
        return getParseUser(KEY_SENDER);
    }

    public void set_reciever(ParseUser r)
    {
        put(KEY_RECIEVER, r);
    }

    public ParseUser get_reciever(){ return getParseUser(KEY_RECIEVER); }
}
