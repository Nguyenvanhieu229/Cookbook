package com.example.btl;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.btl.adapter.SpinerAdapter;
import com.example.btl.adapter.SpinerAdapterMonAn;
import com.example.btl.dal.SQLiteHelper;
import com.example.btl.dal.Utils;
import com.example.btl.model.MonAn;
import com.example.btl.model.User;
import com.example.btl.model.UserLogin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddActivity extends AppCompatActivity {

    private SQLiteHelper db;
    private User user;
    private Button dangTai, huy, btChonAnh;

    private ImageView imageView;

    private byte[] avatar_path;
    private Spinner spinner;

    private EditText name, khauPhan, thoiGian, nguyenLieu, cachLam, moTa, huongDan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
//        Intent intent = getIntent();
//        user = (User) intent.getSerializableExtra("user");

        user = UserLogin.getInstance().getCurrentUser();

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
        btChonAnh = findViewById(R.id.btChonAnh);

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
                try {
                    int mkhauphan = Integer.parseInt(khauPhan.getText().toString());
                    int mthoiGian = Integer.parseInt(thoiGian.getText().toString());
                }
                catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Khẩu phần và thời gian phải là số", Toast.LENGTH_LONG).show();
                    return;
                }
                String mnguyenLieu = nguyenLieu.getText().toString();
                String mcachLam = cachLam.getText().toString();
                String mmota = moTa.getText().toString();
                String mhuongdan = huongDan.getText().toString();
                int mkhauphan = Integer.parseInt(khauPhan.getText().toString());
                int mthoiGian = Integer.parseInt(thoiGian.getText().toString());

                if (mname == "" || mcachLam == "" || mnguyenLieu == "") {
                    Toast.makeText(getApplicationContext(), "Cần nhập đủ tên món, nguyên liệu, cách làm", Toast.LENGTH_LONG).show();
                    return;
                }

                try {
                    if (!mhuongdan.isEmpty()) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(mhuongdan));
                        startActivity(intent);
                    }
                }
                catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Đường dẫn video không thể mở được", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (avatar_path == null) {
                    Toast.makeText(getApplicationContext(), "Cần chọn ảnh món ăn", Toast.LENGTH_LONG).show();
                    return;
                }
                LocalDateTime currentTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String currentTimeString = currentTime.format(formatter);

                MonAn monAn = new MonAn(0, avatar_path, mname, mmota, mkhauphan, mthoiGian, mnguyenLieu, mcachLam,currentTimeString, user, mhuongdan);

                db.addMonAn(monAn);

                Log.d("Thêm", "Đã thêm thành công món" + monAn.getTen());

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