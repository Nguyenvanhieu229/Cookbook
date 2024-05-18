package com.example.btl.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.AddActivity;
import com.example.btl.R;
import com.example.btl.XemMonAn;
import com.example.btl.adapter.MonAnAdapter;
import com.example.btl.dal.SQLiteHelper;
import com.example.btl.model.MonAn;
import com.example.btl.model.User;
import com.example.btl.model.UserLogin;

import java.util.ArrayList;

public class FragmentHome extends Fragment implements MonAnAdapter.MonAnListener, View.OnClickListener{

    private RecyclerView recyclerView;

    private MonAnAdapter adapter;
    private Button btThit, btCa, btHoaQua, btNam, btHaiSan, btBot, btReload;
    private ArrayList<MonAn> monAnList;
    private SQLiteHelper db;

    private User user;

    public FragmentHome() {
        this.user = UserLogin.getInstance().getCurrentUser();
    }



    public FragmentHome(User user) {
        Log.d("fragment home", "ok " + user.getId());
        this.user = user;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btNam = view.findViewById(R.id.btNam);
        btThit = view.findViewById(R.id.btThit);
        btCa = view.findViewById(R.id.btCa);
        btHoaQua = view.findViewById(R.id.btHoaqua);
        btHaiSan = view.findViewById(R.id.btHaiSan);
        btBot = view.findViewById(R.id.btBot);
        btReload = view.findViewById(R.id.reLoad);


        recyclerView = view.findViewById(R.id.recylerHome);
        db = new SQLiteHelper(getContext());
        monAnList = db.getAll();

        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(manager);
        adapter = new MonAnAdapter(monAnList, getContext(), user);
        recyclerView.setAdapter(adapter);
        adapter.setMonAnListener(this);
        btThit.setOnClickListener(this);
        btBot.setOnClickListener(this);
        btCa.setOnClickListener(this);
        btHaiSan.setOnClickListener(this);
        btHoaQua.setOnClickListener(this);
        btNam.setOnClickListener(this);
        btReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monAnList = db.getAll();
                adapter.setMonAnList(monAnList);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        monAnList = db.getAll();
        adapter.setMonAnList(monAnList);
    }

    public ArrayList<MonAn> filter(ArrayList<MonAn> monAnList, String filter) {
        ArrayList<MonAn> res = new ArrayList<>();
        for (MonAn i : monAnList) {
            if(i.getNguyenLieu().toLowerCase().contains(filter.toLowerCase())
            || i.getTen().toLowerCase().contains(filter.toLowerCase())) {
                res.add(i);
            }
        }
        return res;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btBot) {
            adapter.setMonAnList(filter(monAnList, "bot"));
        }
        if (v.getId() == R.id.btThit) {
            adapter.setMonAnList(filter(monAnList, "thịt"));
        }
        if (v.getId() == R.id.btCa) {
            adapter.setMonAnList(filter(monAnList, "cá"));
        }
        if (v.getId() == R.id.btHaiSan) {
            adapter.setMonAnList(filter(monAnList, "hai san"));
        }
        if (v.getId() == R.id.btHoaqua) {
            adapter.setMonAnList(filter(monAnList, "hoa qua"));
        }
        if (v.getId() == R.id.btNam) {
            adapter.setMonAnList(filter(monAnList, "nam"));
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        MonAn monAn = adapter.getItem(position);
        Intent intent = new Intent(getActivity(), XemMonAn.class);
        intent.putExtra("user", user);
        intent.putExtra("monAn", monAn);
        startActivity(intent);
    }
}
