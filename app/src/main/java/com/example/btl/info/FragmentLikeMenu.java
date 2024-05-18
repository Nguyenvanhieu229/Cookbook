package com.example.btl.info;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.R;
import com.example.btl.XemMonAn;
import com.example.btl.adapter.MonAnAdapter;
import com.example.btl.adapter.MonAnQuanLyAdapter;
import com.example.btl.dal.SQLiteHelper;
import com.example.btl.dal.Utils;
import com.example.btl.model.MonAn;
import com.example.btl.model.User;
import com.example.btl.model.UserLogin;

import java.util.ArrayList;

public class FragmentLikeMenu extends Fragment implements MonAnAdapter.MonAnListener, SearchView.OnQueryTextListener {
    private SQLiteHelper db;
    private User user;
    private SearchView searchView;
    private RecyclerView recyclerView;

    private ImageView avatar;
    private TextView name, cbID;

    private TextView soLuotLuu;
    private ArrayList<MonAn> monAnList;

    private MonAnAdapter adapter;

    public FragmentLikeMenu() {
        this.user = UserLogin.getInstance().getCurrentUser();
    }

    public FragmentLikeMenu(User user) {
        Log.d("fragment my menu", "userid " + user.getId());
        this.user = user;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_like_menu, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new SQLiteHelper(getActivity());
        ArrayList<MonAn> all = db.getAll();

        monAnList = new ArrayList<>();
        for(MonAn i : all) {
            if (i.getNguoiViet().getId() == user.getId())
                monAnList.add(i);
        }

        searchView = view.findViewById(R.id.seachViewMyMenu);
        recyclerView = view.findViewById(R.id.recyclerViewMyMenu);
        soLuotLuu = view.findViewById(R.id.tvSoLuotLuu);
        soLuotLuu.setText("" + db.countSavedDishesByUserId(user.getId()));

        name = view.findViewById(R.id.tvName);
        cbID = view.findViewById(R.id.tvCookbookID);
        avatar = view.findViewById(R.id.imgAvatar);

        name.setText(user.getUsername());
        cbID.setText("." + user.getCookbookId());
        avatar.setImageBitmap(Utils.byteArrayToBitmap(user.getHinhDaiDien()));

        adapter = new MonAnAdapter(monAnList, getContext(), user);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setMonAnListener(this);

        searchView.setOnQueryTextListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        MonAn monAn = adapter.getItem(position);
        Intent intent = new Intent(getActivity(), XemMonAn.class);
        intent.putExtra("user", user);
        intent.putExtra("monAn", monAn);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        filter(monAnList, query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filter(monAnList, newText);
        return false;
    }

    public void filter(ArrayList<MonAn> monAnList, String text) {
        ArrayList<MonAn> filterList = new ArrayList<>();
        for (MonAn i : monAnList) {
            if (i.getNguyenLieu().toLowerCase().contains(text.toLowerCase())
                    || i.getNguoiViet().getCookbookId().equalsIgnoreCase(text)
                    || i.getTen().toLowerCase().contains(text.toLowerCase())) {
                filterList.add(i);
            }
        }
        adapter.setMonAnList(filterList);
    }

    @Override
    public void onResume() {
        super.onResume();
        user = UserLogin.getInstance().getCurrentUser();
        monAnList = db.getSavedDishesByUserId(user.getId());

        adapter.setMonAnList(monAnList);
    }
}
