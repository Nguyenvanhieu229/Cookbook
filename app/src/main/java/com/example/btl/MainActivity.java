package com.example.btl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.btl.adapter.ViewPagerAdapter;
import com.example.btl.model.User;
import com.example.btl.model.UserLogin;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView navigationView;
    private ViewPager viewPager;
    private FloatingActionButton btAdd;
    private User user;

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 3333) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền đã được cấp, bạn có thể sử dụng nó ở đây hoặc trong bất kỳ Activity nào khác
                // ...
            } else {
                Toast.makeText(this, "Quyền truy cập vào bộ nhớ ngoại bị từ chối", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    3333);
        } else {
            //Toast.makeText(this, "Quyền đã được cấp", Toast.LENGTH_LONG).show();
        }

        Intent intent = getIntent();

        user = UserLogin.getInstance().getCurrentUser();

        Log.d("main activity", "ok " + user.getId());

        viewPager = findViewById(R.id.viewPager);

        navigationView = findViewById(R.id.navigation);
        btAdd = findViewById(R.id.fab);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), 4, user);
        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        navigationView.getMenu().findItem(R.id.mHome).setChecked(true);
                        break;
                    case 1:
                        navigationView.getMenu().findItem(R.id.mSearch).setChecked(true);
                        break;
                    case 2:
                        navigationView.getMenu().findItem(R.id.mNoti).setChecked(true);
                        break;
                    case 3:
                        navigationView.getMenu().findItem(R.id.mInfo).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.mHome)
                    viewPager.setCurrentItem(0);
                if (id == R.id.mSearch)
                    viewPager.setCurrentItem(1);
                if (id == R.id.mNoti)
                    viewPager.setCurrentItem(2);
                if (id == R.id.mInfo)
                    viewPager.setCurrentItem(3);
                return true;
            }
        });
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, AddActivity.class);
                intent1.putExtra("user", user);
                startActivity(intent1);
            }
        });
    }


}