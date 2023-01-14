package com.example.bookclub.parse_models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Club")
public class Club extends ParseObject
{
    public static final String KEY_NAME = "name";
    public static final String KEY_IMAGE = "picture";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_CREATED_AT = "createdAt";

    public Club(){}

    public void set_name(String n)
    {
        put(KEY_NAME, n);
    }

    public String get_name()
    {
        return getString(KEY_NAME);
    }

    public void set_picture(ParseFile i)
    {
        put(KEY_IMAGE, i);
    }

    public ParseFile get_picture()
    {
        return getParseFile(KEY_IMAGE);
    }

    public void set_description(String d)
    {
        put(KEY_DESCRIPTION, d);
    }

    public String get_description() { return getString(KEY_DESCRIPTION); }
}
