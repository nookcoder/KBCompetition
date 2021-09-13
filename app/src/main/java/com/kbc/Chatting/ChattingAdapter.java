package com.kbc.Chatting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kbc.Chatting.Chatting;
import com.kbc.Chatting.Chatting_Item;
import com.kbc.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChattingAdapter extends BaseAdapter {

    //채팅 아이템
    ArrayList<Chatting_Item> chatting_items;
   //정렬
    LayoutInflater layoutInflater;

    //생성자
    public ChattingAdapter(ArrayList<Chatting_Item> chatting_items, LayoutInflater layoutInflater){
        this.chatting_items = chatting_items;
        this.layoutInflater = layoutInflater;
    }


    @Override
    public int getCount() {
        return chatting_items.size();
    }

    @Override
    public Object getItem(int position) {
        return chatting_items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //현재 채팅 위치에 뷰 생성
        Chatting_Item chatting_item = chatting_items.get(position);
        //재사용 안함
        View itemView = null;

        //나의 메세지라면,,
        if(chatting_item.getName().equals(Chatting.userName)){
            itemView = layoutInflater.inflate(R.layout.chatting_me_message_box, parent,false);
        }
        //상대방이라면
        else {
            itemView = layoutInflater.inflate(R.layout.chatting_other_message_box, parent,false);
        }

        CircleImageView chatting_circle_ImageView = itemView.findViewById((R.id.chatting_circle_imageview));
        TextView chatting_name = itemView.findViewById(R.id.chatting_name);
        TextView chatting_message = itemView.findViewById(R.id.chatting_send_message);
        TextView chatting_send_message_time = itemView.findViewById(R.id.chatting_send_message_time);

        chatting_name.setText((chatting_item.getName()));
        chatting_message.setText(chatting_item.getMessage());
        chatting_send_message_time.setText(chatting_item.getTime());

        //itemView에 사용자이름, 보낸시간, 메세지, 이미지 설정
        Glide.with(itemView).load(chatting_item.getProfileUrl()).into(chatting_circle_ImageView);

        return itemView;

    }

}
