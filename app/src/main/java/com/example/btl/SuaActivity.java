package com.example.btl;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btl.adapter.SpinerAdapterMonAn;
import com.example.btl.dal.SQLiteHelper;
import com.example.btl.dal.Utils;
import com.example.btl.model.MonAn;
import com.example.btl.model.User;
import com.example.btl.model.UserLogin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SuaActivity extends AppCompatActivity {

    private SQLiteHelper db;
    private User user;
    private MonAn monAn;
    private Button dangTai, huy;

    private byte[] avatar_path;

    private ImageView imageView;
    private Spinner spinner;

    private EditText name, khauPhan, thoiGian, nguyenLieu, cachLam, moTa, huongDan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua);

        Intent intent = getIntent();
//        user = (User) intent.getSerializableExtra("user");

        user = UserLogin.getInstance().getCurrentUser();

        monAn = (MonAn) intent.getSerializableExtra("monAn");
        db = new SQLiteHelper(this);

        initView();
    }

    public void initView() {
        dangTai = findViewById(R.id.btLuuAdd);
        huy = findViewById(R.id.btCancelAdd);
        imageView = findViewById(R.id.imgAdd);
//        spinner = findViewById(R.id.spAdd);
        name = findViewById(R.id.nameAdd);
        khauPhan = findViewById(R.id.khauPhanAdd);
        thoiGian = findViewById(R.id.thoiGianNauAdd);
        nguyenLieu = findViewById(R.id.nguyenLieuAdd);
        cachLam = findViewById(R.id.cachLamAdd);
        moTa = findViewById(R.id.moTaAdd);
        huongDan = findViewById(R.id.huongDanAdd);

//        SpinerAdapterMonAn adapterMonAn = new SpinerAdapterMonAn(this);
//        spinner.setAdapter(adapterMonAn);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                imageView.setImageResource((Integer) adapterMonAn.getItem(position));
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        dangTai.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
//                int img = Integer.parseInt(spinner.getSelectedItem().toString());
//                imageView.setImageResource(img);

                String mname = name.getText().toString();
                int mkhauphan = Integer.parseInt(khauPhan.getText().toString());
                int mthoiGian = Integer.parseInt(thoiGian.getText().toString());
                String mnguyenLieu = nguyenLieu.getText().toString();
                String mcachLam = cachLam.getText().toString();
                String mmota = moTa.getText().toString();
                String mhuongdan = huongDan.getText().toString();

                LocalDateTime currentTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String currentTimeString = currentTime.format(formatter);

                MonAn capNhat = new MonAn(monAn.getId(), avatar_path, mname, mmota, mkhauphan, mthoiGian, mnguyenLieu, mcachLam,currentTimeString, user, mhuongdan);


                db.updateMonAn(capNhat);

                Log.d("Thêm", "Đã cập nhật thành công món" + monAn.getTen());

                dangTai.setEnabled(false);

                finish();

            }
        });

        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public void chooseImage(View view) {
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

            imageView.setImageURI(uri);
        }
    }
}
