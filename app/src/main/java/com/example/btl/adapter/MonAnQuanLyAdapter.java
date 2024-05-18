package com.example.btl.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.R;
import com.example.btl.SuaActivity;
import com.example.btl.dal.SQLiteHelper;
import com.example.btl.dal.Utils;
import com.example.btl.model.MonAn;
import com.example.btl.model.User;
import com.example.btl.model.UserLogin;

import java.util.ArrayList;

public class MonAnQuanLyAdapter extends RecyclerView.Adapter<MonAnQuanLyAdapter.MonAnViewHolder> {

    private SQLiteHelper db;
    private ArrayList<MonAn> monAnList;
    private Context context;

    private MonAnListener monAnListener;
    private Activity activity;

    private User user;

    public ArrayList<MonAn> getMonAnList() {
        return monAnList;
    }



    public MonAnQuanLyAdapter(ArrayList<MonAn> monAnList, Context context, User user, Activity activity) {
        this.monAnList = monAnList;
        this.context = context;
        this.user = user;
        this.db = new SQLiteHelper(context);
        this.activity = activity;

    }

    public MonAnQuanLyAdapter() {
        user = UserLogin.getInstance().getCurrentUser();
    }

    public void setMonAnListener(MonAnListener monAnListener) {
        this.monAnListener = monAnListener;
    }

    public void setMonAnList(ArrayList<MonAn> monAnList) {
        this.monAnList = monAnList;
        notifyDataSetChanged();
    }

    public MonAn getItem(int position) {
        return monAnList.get(position);
    }

    @NonNull
    @Override
    public MonAnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mon_an_de_quan_ly, parent, false);
        return new MonAnViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MonAnViewHolder holder, int position){
        MonAn monAn = monAnList.get(position);

        if(monAn == null) return;

        holder.monAnChuName.setText(monAn.getNguoiViet().getUsername());
        holder.monAnName.setText(monAn.getTen());
        holder.linearLayout.setBackgroundDrawable(Utils.byteArrayToDrawable(monAn.getHinhDaiDien()));
        holder.monAnChuImage.setImageBitmap(Utils.byteArrayToBitmap(monAn.getNguoiViet().getHinhDaiDien()));


        holder.btSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SuaActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("monAn", monAn);
                activity.startActivity(intent);
            }
        });

        holder.btXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo xóa");
                builder.setMessage("Ban co chac chan xoa khong?" + monAn.getTen());
                builder.setIcon(R.drawable.img);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteMonAnById(monAn.getId());
                        monAnList.remove(position);
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return monAnList.size();
    }

    public class MonAnViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private LinearLayout linearLayout;
        private ImageView monAnChuImage;
        private TextView monAnName, monAnChuName;

        private Button btSua, btXoa;



        public MonAnViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.layoutMonAn);
            monAnChuImage = itemView.findViewById(R.id.monAnChuImage);
            monAnName = itemView.findViewById(R.id.monAnName);
            monAnChuName = itemView.findViewById(R.id.monAnTenChu);
            btSua = itemView.findViewById(R.id.btSua);
            btXoa = itemView.findViewById(R.id.btXoa);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (monAnListener != null) {
                monAnListener.onItemClick(v, getAdapterPosition());
            }
        }
    }
    public interface MonAnListener {
        void onItemClick(View view, int position);
    }

}
