package com.example.btl.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.btl.R;
import com.example.btl.info.InfoViewPagerAdapter;
import com.example.btl.model.User;
import com.example.btl.model.UserLogin;
import com.google.android.material.tabs.TabLayout;

public class FragmentInfo extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;

    private User user;

    public FragmentInfo() {
        this.user = UserLogin.getInstance().getCurrentUser();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public FragmentInfo(User user) {
        Log.d("fragment info", "ok " + user.getId());
        this.user = user;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_info, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPagerInfo);
        InfoViewPagerAdapter adapter = new InfoViewPagerAdapter(getChildFragmentManager(), 2, user);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
