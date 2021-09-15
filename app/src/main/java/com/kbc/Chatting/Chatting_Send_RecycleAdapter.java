package com.kbc.Chatting;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kbc.Chatting.Chatting;
import com.kbc.Chatting.Chatting_Item;
import com.kbc.Chatting.Chatting_List_RecycleAdapter;
import com.kbc.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chatting_Send_RecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //채팅방 데이터 추가
    private ArrayList<Chatting_Item> chatting_items ;

    public Chatting_Send_RecycleAdapter(ArrayList<Chatting_Item>chatting_items){
        this.chatting_items = chatting_items;
    }

    //오른쪽 왼쪽 나눠서 뷰홀더 꾸미기
    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int viewType) {
        View view;
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (viewType == Chatting.CENTER_CONTENT){
            view = layoutInflater.inflate(R.layout.chatting_date_box, viewGroup, false);
            return new Center_ViewHolder(view);
        }
        else if(viewType == Chatting.LEFT_CONTENT){
            view = layoutInflater.inflate(R.layout.chatting_other_message_box, viewGroup,false);
            return new Left_ViewHolder(view);
        }
        else {
            view = layoutInflater.inflate(R.layout.chatting_me_message_box, viewGroup,false);
            return new Right_ViewHolder(view);
        }

    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof Center_ViewHolder){
            ((Center_ViewHolder)viewHolder).chatting_date.setText((chatting_items.get(position).getDate()));
        }
        else if(viewHolder instanceof Left_ViewHolder){
//             ((Left_ViewHolder)send_viewHolder).chatting_circle_imageview.setImageURI(chatting_items.get(position).getProfileUrl());

            ((Left_ViewHolder)viewHolder).chatting_name.setText(chatting_items.get(position).getName());
            ((Left_ViewHolder)viewHolder).chatting_send_message.setText(chatting_items.get(position).getMessage());;
            ((Left_ViewHolder)viewHolder).chatting_send_message_time.setText(chatting_items.get(position).getTime());
            ((Left_ViewHolder)viewHolder).itemView.setTag(position);
        }
        else {
            //             ((Right_ViewHolder)send_viewHolder).chatting_circle_imageview.setImageURI(chatting_items.get(position).getProfileUrl());
            ((Right_ViewHolder)viewHolder).chatting_name.setText(chatting_items.get(position).getName());
            ((Right_ViewHolder)viewHolder).chatting_send_message.setText(chatting_items.get(position).getMessage());;
            ((Right_ViewHolder)viewHolder).chatting_send_message_time.setText(chatting_items.get(position).getTime());
            ((Right_ViewHolder)viewHolder).itemView.setTag(position);

        }
    }

    @Override
    public int getItemViewType(int position) {
        return chatting_items.get(position).getViewType();
    }

    @Override
    public int getItemCount() {

        if(chatting_items != null){
            return chatting_items.size();
        }

        return 0;
    }

    public void addItem(Chatting_Item chatting_Item){
        chatting_items.add(chatting_Item);

    }


    public class Center_ViewHolder extends RecyclerView.ViewHolder{

        TextView chatting_date;

        public Center_ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            chatting_date = itemView.findViewById(R.id.date_content);
        }
    }
    public class Left_ViewHolder extends RecyclerView.ViewHolder{

        //프로필 사진, 이름, 메세지, 보낸 시간
         CircleImageView chatting_circle_imageview;
         TextView chatting_name;
         TextView chatting_send_message;
         TextView chatting_send_message_time;

        public Left_ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            chatting_circle_imageview =itemView.findViewById(R.id.chatting_circle_imageview);
            chatting_name = itemView.findViewById(R.id.chatting_name);
            chatting_send_message = itemView.findViewById(R.id.chatting_send_message);
            chatting_send_message_time = itemView.findViewById(R.id.chatting_send_message_time);


        }
    }

    public class Right_ViewHolder extends RecyclerView.ViewHolder{
        //프로필 사진, 이름, 메세지, 보낸 시간
        CircleImageView chatting_circle_imageview;
         TextView chatting_name;
         TextView chatting_send_message;
         TextView chatting_send_message_time;

        public Right_ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);


            chatting_circle_imageview = itemView.findViewById(R.id.chatting_circle_imageview);
            chatting_name = itemView.findViewById(R.id.chatting_name);
            chatting_send_message = itemView.findViewById(R.id.chatting_send_message);
            chatting_send_message_time = itemView.findViewById(R.id.chatting_send_message_time);


        }
    }


}
