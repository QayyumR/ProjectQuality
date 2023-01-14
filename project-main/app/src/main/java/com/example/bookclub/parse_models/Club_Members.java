package com.example.bookclub.parse_models;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Club_Members")
public class Club_Members extends ParseObject
{
    public static final String KEY_CLUB = "club";
    public static final String KEY_MEMBER = "member";
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

    public void set_member(ParseUser m) { put(KEY_MEMBER, m); }

    public ParseUser get_member() { return getParseUser(KEY_MEMBER); }
}
