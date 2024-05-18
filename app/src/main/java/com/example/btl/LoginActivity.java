package com.example.btl;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.btl.dal.SQLiteHelper;
import com.example.btl.model.User;
import com.example.btl.model.UserLogin;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText txtUsername, txtPassword;
    Button btLogin, btRegister;
    private User user;

    private SQLiteHelper db;

    private final static int REQUEST_CODE = 2002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    3333);
        } else {
        }
    }

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
                //Toast.makeText(this, "Quyền truy cập vào bộ nhớ ngoại bị từ chối", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void initView() {
        txtPassword = findViewById(R.id.txtPassword);
        txtUsername = findViewById(R.id.txtUsername);
        btLogin = findViewById(R.id.btLogin);
        btRegister = findViewById(R.id.btRegister);
        btRegister.setOnClickListener(this);
        btLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btLogin) {

            String username = txtUsername.getText().toString();
            String password = txtPassword.getText().toString();
            if (username == null || password == null) {
                Toast.makeText(this, "Nhập username và password", Toast.LENGTH_LONG).show();
                return;
            }


            db = new SQLiteHelper(this);
            user = db.findUserByUsernameAndPassword(username, password);


            if (user == null ) {
                Toast.makeText(this, "Thông tin đăng nhập sai", Toast.LENGTH_LONG).show();
                return;
            }

            Log.d("User in login activity", "" + user.getId());

            // thêm vào lớp singleton
            UserLogin userLogin = UserLogin.getInstance();
            userLogin.setCurrentUser(user);

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }
        if (v.getId() == R.id.btRegister) {
            Intent resIntent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivityForResult(resIntent, REQUEST_CODE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE == requestCode && resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Bạn đã hủy đăng ký", Toast.LENGTH_LONG).show();
            }
            else {
                user = (User) data.getSerializableExtra("user");
                txtUsername.setText(user.getUsername());
                txtPassword.setText(user.getPassword());
            }
        }
    }
}