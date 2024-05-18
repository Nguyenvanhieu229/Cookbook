package com.example.btl.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.btl.fragment.FragmentHome;
import com.example.btl.fragment.FragmentInfo;
import com.example.btl.fragment.FragmentNotification;
import com.example.btl.fragment.FragmentSearch;
import com.example.btl.model.User;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    int pagenum;
    User user;


    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior, User user) {
        super(fm, behavior);
        this.user = user;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentHome();
            case 1:
                return new FragmentSearch();
            case 2:
                return new FragmentNotification();
            case 3:
                return new FragmentInfo();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
