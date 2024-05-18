package com.example.btl;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.btl.adapter.SpinerAdapter;
import com.example.btl.adapter.SpinerAdapterMonAn;
import com.example.btl.dal.SQLiteHelper;
import com.example.btl.model.MonAn;
import com.example.btl.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddActivity extends AppCompatActivity {

    private SQLiteHelper db;
    private User user;
    private MonAn monAn;
    private Button dangTai, huy;

    private ImageView imageView;
    private Spinner spinner;

    private EditText name, khauPhan, thoiGian, nguyenLieu, cachLam, moTa, huongDan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        monAn = (MonAn) intent.getSerializableExtra("monAn");
        db = new SQLiteHelper(this);

        initView();
    }

    public void initView() {
        dangTai = findViewById(R.id.btLuuAdd);
        huy = findViewById(R.id.btCancelAdd);
        imageView = findViewById(R.id.imgAdd);
        spinner = findViewById(R.id.spAdd);
        name = findViewById(R.id.nameAdd);
        khauPhan = findViewById(R.id.khauPhanAdd);
        thoiGian = findViewById(R.id.thoiGianNauAdd);
        nguyenLieu = findViewById(R.id.nguyenLieuAdd);
        cachLam = findViewById(R.id.cachLamAdd);
        moTa = findViewById(R.id.moTaAdd);
        huongDan = findViewById(R.id.huongDanAdd);

        SpinerAdapterMonAn adapterMonAn = new SpinerAdapterMonAn(this);
        spinner.setAdapter(adapterMonAn);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imageView.setImageResource((Integer) adapterMonAn.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dangTai.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                int img = Integer.parseInt(spinner.getSelectedItem().toString());
                imageView.setImageResource(img);

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

                monAn

                db.addMonAn(monAn);

                Log.d("Thêm", "Đã thêm thành công món" + monAn.getTen());

                dangTai.setEnabled(false);
                finish();

            }
        });

    }
}