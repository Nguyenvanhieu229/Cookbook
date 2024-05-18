package com.example.btl.info;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.btl.model.User;

import java.util.ArrayList;


public class InfoViewPagerAdapter extends FragmentStatePagerAdapter {

    private User user;

    public InfoViewPagerAdapter(@NonNull FragmentManager fm, int behavior, User user) {
        super(fm, behavior);
        this.user = user;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        if (position == 0)
            return new FragmentMyMenu();

        return new FragmentLikeMenu();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0)
            return "Món ăn của tôi";
        return "Món ăn đã lưu";
    }
}
