package com.example.bookclub.parse_models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Club_Message")
public class Club_Message extends ParseObject
{
    public static final String KEY_CLUB = "club";
    public static final String KEY_SENDER = "sender";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_CREATED_AT = "createdAt";

    public void set_club(Club c)
    {
        put(KEY_CLUB, c);
    }

    public Club get_club()
    {
        Club c = new Club();

        ParseObject po = getParseObject(KEY_CLUB);

        c.set_name(po.getString(Club.KEY_NAME));
        c.set_description(po.getString(Club.KEY_DESCRIPTION));

        return c;
    }

    public void set_sender(ParseUser s)
    {
        put(KEY_SENDER, s);
    }

    public ParseUser get_sender()
    {
        return getParseUser(KEY_SENDER);
    }

    public void set_message(String m)
    {
        put(KEY_MESSAGE, m);
    }

    public String get_message(){ return getString(KEY_MESSAGE); }
}
