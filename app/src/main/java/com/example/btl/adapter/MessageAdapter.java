package com.example.btl.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.R;
import com.example.btl.dal.Utils;
import com.example.btl.model.Message;

import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{
    private Context context;
    private List<Message> messageList;

    public MessageAdapter(Context context, List<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
//        Log.d("Num message", "" + messageList.size());

    }


    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messageList.get(position);
        holder.name.setText(message.getUser().getUsername());
        holder.img.setImageBitmap(Utils.byteArrayToBitmap(message.getMonAn().getHinhDaiDien()));
        holder.title.setText(message.getTitle());
        holder.time.setText(message.getTime());
    }


    @Override
    public int getItemCount() {
        return messageList.size();
    }


    public class MessageViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name, title, time;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.messImg);
            name = itemView.findViewById(R.id.messName);
            title = itemView.findViewById(R.id.messTitle);
            time = itemView.findViewById(R.id.messGio);
        }
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
        notifyDataSetChanged();
    }
}
