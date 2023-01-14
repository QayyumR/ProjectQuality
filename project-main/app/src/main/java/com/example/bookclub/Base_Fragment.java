package com.example.bookclub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Base_Fragment extends Fragment
{
    public Base_Fragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflate_view(inflater, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        init_components(view);
    }

    protected View inflate_view(LayoutInflater i, ViewGroup c)
    {
        return i.inflate(R.layout.fragment_profile, c, false);
    }

    protected void init_components(View v){}
}
