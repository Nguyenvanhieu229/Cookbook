package com.example.btl;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl.dal.SQLiteHelper;
import com.example.btl.dal.Utils;
import com.example.btl.model.User;
import com.example.btl.model.UserLogin;

public class UserinfoActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtUsernameRes, txtPasswordRes, txtEmailRes, txtCookbookId, txtChiase;
    private ImageView imgAvatar;

    private User user;

    private Button btRegister, btAvatar;

    private Spinner spinner;

    private SQLiteHelper db;

    private byte[] avatar_path;

    private int[] imgs = {R.drawable.img, R.drawable.img_1, R.drawable.img_2, R.drawable.img_3, R.drawable.img_4, R.drawable.default_avatar};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        this.user = UserLogin.getInstance().getCurrentUser();
        initView();
    }

    public void initView() {
        txtEmailRes = findViewById(R.id.txtEmailRes);
        txtUsernameRes = findViewById(R.id.txtUsernameRes);
        txtPasswordRes = findViewById(R.id.txtPasswordRes);
        txtCookbookId = findViewById(R.id.txtCookbookId);
        txtChiase = findViewById(R.id.txtChiase);
        btRegister = findViewById(R.id.btRegister);
        btAvatar = findViewById(R.id.buttonChooseImage);
        imgAvatar = findViewById(R.id.imgAvatar);
        btRegister.setOnClickListener(this);

        txtEmailRes.setText(user.getEmail());
        txtUsernameRes.setText(user.getUsername());
        txtPasswordRes.setText(user.getPassword());
        txtCookbookId.setText(user.getCookbookId());
        txtChiase.setText(user.getChiaSeChung());
        imgAvatar.setImageBitmap(Utils.byteArrayToBitmap(user.getHinhDaiDien()));
        avatar_path = user.getHinhDaiDien();


//        spinner = findViewById(R.id.sp);
//        SpinerAdapter spinerAdapter = new SpinerAdapter(this);
//        spinner.setAdapter(spinerAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btRegister) {
            String username = txtUsernameRes.getText().toString();
            String password = txtPasswordRes.getText().toString();
            String email = txtEmailRes.getText().toString();
            String cookBookId = txtCookbookId.getText().toString();
            String chiaSe = txtChiase.getText().toString();
            if (username == null || password == null || email == null || cookBookId == null
            ) {
                Toast.makeText(this, "Bạn cần điền đủ thông tin", Toast.LENGTH_LONG).show();
                return;
            }

            if (avatar_path == null)
            {
                Toast.makeText(this, "Bạn cần chọn avatar", Toast.LENGTH_LONG).show();
                return;
            }

            String email_regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            String cb_id_regex = "^@.{1,10}$";

            if(!email.matches(email_regex))
            {
                Toast.makeText(this, "Hãy nhập email đúng định dạng: example@example.com", Toast.LENGTH_LONG).show();
                return;
            }

            if(!cookBookId.matches(cb_id_regex))
            {
                Toast.makeText(this, "Hãy nhập cookbookId đúng định dạng: @example", Toast.LENGTH_LONG).show();
                return;
            }

            User user_update = new User(user.getId(), username, password, cookBookId, email, chiaSe, avatar_path);

            db = new SQLiteHelper(this);
            db.updateUser(user);

            UserLogin.getInstance().setCurrentUser(user_update);

            Intent intent = new Intent();
            intent.putExtra("user", user_update);

            setResult(RESULT_OK, intent);
            finish();
        }

        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();

    }

    public void chooseImageFromGallery(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1111);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1111 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            avatar_path = Utils.uriToByteArray(this, uri);

            Toast.makeText(this, "bạn đã chọn" + avatar_path, Toast.LENGTH_LONG).show();

            imgAvatar.setImageURI(uri);
        }
    }


}