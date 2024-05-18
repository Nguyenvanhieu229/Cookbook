package com.example.btl.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.R;
import com.example.btl.adapter.MessageAdapter;
import com.example.btl.dal.SQLiteHelper;
import com.example.btl.model.Message;
import com.example.btl.model.User;
import com.example.btl.model.UserLogin;

import java.util.ArrayList;


public class FragmentNotification extends Fragment {

    private SQLiteHelper db;

    MessageAdapter messageAdapter;
    RecyclerView recyclerView;
    ArrayList<Message> messageList;
    private User user;

    public FragmentNotification() {
        this.user = UserLogin.getInstance().getCurrentUser();
    }

    public FragmentNotification(User user) {
        Log.d("fragment noti", "ok " + user.getId());
        this.user = user;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_noti, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = new SQLiteHelper(getContext());
        ArrayList<Message> all = db.getAllMessages();

        messageList = new ArrayList<>();
        for (Message i : all) {
            if (i != null && i.getMonAn() != null && i.getMonAn().getNguoiViet().getId() == user.getId())
                messageList.add(i);
        }

        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false);
        messageAdapter = new MessageAdapter(view.getContext(), messageList);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(messageAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        ArrayList<Message> all = db.getAllMessages();

        messageList = new ArrayList<>();
        for (Message i : all) {
            if (i != null && i.getMonAn() != null && i.getMonAn().getNguoiViet().getId() == user.getId())
                messageList.add(i);
        }
        messageAdapter.setMessageList(messageList);

    }
}
