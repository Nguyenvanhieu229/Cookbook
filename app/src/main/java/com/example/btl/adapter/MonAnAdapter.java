package com.example.btl.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.R;
import com.example.btl.dal.SQLiteHelper;
import com.example.btl.dal.Utils;
import com.example.btl.model.Message;
import com.example.btl.model.MonAn;
import com.example.btl.model.User;
import com.example.btl.model.UserLogin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MonAnAdapter extends RecyclerView.Adapter<MonAnAdapter.MonAnViewHolder> {
    private ArrayList<MonAn> monAnList;
    private ArrayList<Integer> idDaLuu;
    private SQLiteHelper db;
    private Context context;

    private MonAnListener monAnListener;

    private User user;

    public ArrayList<MonAn> getMonAnList() {
        return monAnList;
    }

    public MonAnAdapter(ArrayList<MonAn> monAnList, Context context, User user) {
        this.monAnList = monAnList;
        this.context = context;
        this.db = new SQLiteHelper(context);
        this.user = user;
        if (user == null)
            user = UserLogin.getInstance().getCurrentUser();
        this.idDaLuu = db.getAllSavedMonAnIdsByUser(user);
    }

    public MonAnAdapter() {
    }

    public void setMonAnListener(MonAnListener monAnListener) {
        this.monAnListener = monAnListener;
    }

    public void setMonAnList(ArrayList<MonAn> monAnList) {
        this.monAnList = monAnList;
        this.idDaLuu = db.getAllSavedMonAnIdsByUser(user);
        notifyDataSetChanged();
    }

    public MonAn getItem(int position) {
        return monAnList.get(position);
    }

    @NonNull
    @Override
    public MonAnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mon_an, parent, false);
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

        holder.btKhongThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("luu", "Không thích" + user.getUsername()) ;
                db.deleteSavedDishByUserIdAndDishId(user.getId(), monAnList.get(position).getId());
                monAnList.remove(position);
                notifyDataSetChanged();
            }
        });

        if (idDaLuu.contains(monAnList.get(position).getId())) {
            holder.btThich.setText("Đã lưu");
            holder.btThich.setClickable(false);
        }

        holder.btThich.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Log.d("thich", "Thich" + user.getUsername());
                Boolean res = db.luuMonAn(user, monAn);
                if (res == false) {
                    Toast.makeText(context, "Món ăn đã lưu trước đó, không lưu lại!", Toast.LENGTH_LONG).show();
                    return;
                }
                holder.btThich.setText("ĐÃ LƯU");
                LocalDateTime currentTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String currentTimeString = currentTime.format(formatter);

                Message message = new Message(0, user, monAn, "Đã lưu món ăn của bạn", currentTimeString);
                int id = (int) db.saveMessage(message);
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

        private Button btKhongThich, btThich;



        public MonAnViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.layoutMonAn);
            monAnChuImage = itemView.findViewById(R.id.monAnChuImage);
            monAnName = itemView.findViewById(R.id.monAnName);
            monAnChuName = itemView.findViewById(R.id.monAnTenChu);
            btKhongThich = itemView.findViewById(R.id.btKhongThich);
            btThich = itemView.findViewById(R.id.btThich);

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
