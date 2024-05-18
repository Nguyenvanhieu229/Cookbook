package com.example.btl;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl.dal.SQLiteHelper;
import com.example.btl.dal.Utils;
import com.example.btl.model.Message;
import com.example.btl.model.MonAn;
import com.example.btl.model.User;
import com.example.btl.model.UserLogin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class XemMonAn extends AppCompatActivity {
    private SQLiteHelper db;
    private User user;
    private MonAn monAn;
    private ImageView imgChiTiet, imgChuChiTiet;
    private TextView nameChiTiet, nameChuChiTiet, tagChuChiTiet, chiaSeChiTiet;

    private TextView khauPhanChiTiet, thoiGianChiTiet, nguyenLieuChiTiet, cachLamChiTiet;

    private Button btLuuChiTiet, btBinhLuan, btHuongDan;

    private EditText title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_mon_an);

        Intent intent = getIntent();
//        user = (User) intent.getSerializableExtra("user");
        user = UserLogin.getInstance().getCurrentUser();
        monAn = (MonAn) intent.getSerializableExtra("monAn");
        db = new SQLiteHelper(this);

        initView();

    }

    public void initView() {
        imgChiTiet = findViewById(R.id.imgChiTiet);
        imgChuChiTiet = findViewById(R.id.imgChuChiTiet);
        nameChiTiet = findViewById(R.id.nameChiTiet);
        nameChuChiTiet = findViewById(R.id.nameChuChiTiet);
        tagChuChiTiet = findViewById(R.id.tagChuChiTiet);
        chiaSeChiTiet = findViewById(R.id.chiaSeChiTiet);
        khauPhanChiTiet = findViewById(R.id.khauphanChiTiet);
        thoiGianChiTiet = findViewById(R.id.thoigianChiTiet);
        nguyenLieuChiTiet = findViewById(R.id.nguyenlieuChiTiet);
        cachLamChiTiet = findViewById(R.id.cachLamChiTiet);
        btLuuChiTiet = findViewById(R.id.btLuuChiTiet);
        btBinhLuan = findViewById(R.id.binhLuan);
        btHuongDan = findViewById(R.id.btHuongDanChiTiet);
        title = findViewById(R.id.title);

        imgChiTiet.setImageBitmap(Utils.byteArrayToBitmap(monAn.getHinhDaiDien()));
        imgChuChiTiet.setImageBitmap(Utils.byteArrayToBitmap(monAn.getNguoiViet().getHinhDaiDien()));
        nameChiTiet.setText(monAn.getTen());
        nameChuChiTiet.setText(monAn.getNguoiViet().getUsername());
        tagChuChiTiet.setText(monAn.getNguoiViet().getCookbookId());
        chiaSeChiTiet.setText(monAn.getNguoiViet().getChiaSeChung());
        khauPhanChiTiet.setText(monAn.getKhauPhan() + " người ăn");
        thoiGianChiTiet.setText(monAn.getThoiGianNau() + " phút");
        nguyenLieuChiTiet.setText(monAn.getNguyenLieu());
        cachLamChiTiet.setText(monAn.getCachLam());

        btLuuChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.luuMonAn(user, monAn);
                btLuuChiTiet.setText("ĐÃ LƯU");
            }
        });


        btBinhLuan.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String title_str = title.getText().toString();
                if (title_str == null || title_str.length() == 0)
                    return;

                LocalDateTime currentTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String currentTimeString = currentTime.format(formatter);

                Message message = new Message(0, user, monAn, title_str,currentTimeString);
                int id = (int) db.saveMessage(message);

                message.setId(id);

                title.setText(message.getTitle());
                title.setEnabled(false);
                btBinhLuan.setEnabled(false);
            }
        });

        btHuongDan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(monAn.getHuongDan()));
                    startActivity(intent);
                }
                catch (Exception e) {
                    Toast.makeText(XemMonAn.this, "Rất tiếc món ăn này không có video hướng dẫn", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}