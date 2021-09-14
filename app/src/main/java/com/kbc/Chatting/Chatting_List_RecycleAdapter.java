package com.kbc.Chatting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kbc.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Chatting_List_RecycleAdapter extends RecyclerView.Adapter<Chatting_List_RecycleAdapter.ViewHolder> {

    //채팅방 데이터 추가
    private ArrayList<Chatting_Item> chatting_items = null;

    public Chatting_List_RecycleAdapter(ArrayList<Chatting_Item> chattingItems){
        chatting_items = chattingItems;
    }

    //아이템 뷰를 위한 뷰홀더 객체를 형성 -> 레이아웃 생성
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.chatting_list_item, viewGroup,false);
        Chatting_List_RecycleAdapter.ViewHolder viewHolder = new Chatting_List_RecycleAdapter.ViewHolder(view);

        return viewHolder;
    }

    //뷰홀더의 position에 해당하는 데이터를 뷰홀더의 아이템 뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.
    }

    //아이템 개수 조회
   @Override
    public int getItemCount() {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
