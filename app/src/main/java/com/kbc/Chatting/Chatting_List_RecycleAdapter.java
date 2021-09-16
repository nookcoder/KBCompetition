package com.kbc.Chatting;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kbc.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chatting_List_RecycleAdapter extends RecyclerView.Adapter<Chatting_List_RecycleAdapter.ViewHolder> {

    public interface OnItemClickEventListener {
        void onItemClick(View view, int position);
    }

    //채팅방 데이터 추가
    private ArrayList<Chatting_Item> chatting_items;
    private OnItemClickEventListener onItemClickEventListener;



    public Chatting_List_RecycleAdapter(ArrayList<Chatting_Item> chatting_items, OnItemClickEventListener onItemClickEventListener){
        this.chatting_items = chatting_items;
        this.onItemClickEventListener = onItemClickEventListener;
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
//        holder.chatting_list_image_profil.setImageURI(chatting_items.get(position).getProfileUrl()));
        holder.chatting_list_name.setText(chatting_items.get(position).getName());
        holder.chatting_list_send_message.setText(chatting_items.get(position).getMessage());
        holder.chatting_list_send_message_time.setText(chatting_items.get(position).getTime());
        holder.chatting_list_send_message_date.setText(chatting_items.get(position).getDate());

        holder.itemView.setTag(position);
    }

    //아이템 개수 조회
   @Override
    public int getItemCount() {
        return chatting_items.size();
    }

    public void addItem(Chatting_Item chatting_item){
        chatting_items.add(chatting_item);
        notifyDataSetChanged();
    }

    public String getItemName(int index){return chatting_items.get(index).getName();}

    public void removeAll(){
        chatting_items.clear();
    }


    //요소부르고
    public class ViewHolder extends RecyclerView.ViewHolder {

        //프로필 사진, 이름, 메세지, 보낸 시간
        protected CircleImageView chatting_list_image_profil;
        protected TextView chatting_list_name;
        protected TextView chatting_list_send_message;
        protected TextView chatting_list_send_message_time;
        protected TextView chatting_list_send_message_date;


        public ViewHolder(@NonNull @NotNull View itemView) {

            super(itemView);

            this.chatting_list_image_profil = (CircleImageView)itemView.findViewById(R.id.chatting_circle_imageview);
            this.chatting_list_name = (TextView)itemView.findViewById(R.id.chatting_list_name);
            this.chatting_list_send_message = (TextView)itemView.findViewById(R.id.chatting_list_send_message);
            this.chatting_list_send_message_time = (TextView)itemView.findViewById(R.id.chatting_list_send_message_time);
            this.chatting_list_send_message_date = (TextView)itemView.findViewById(R.id.chatting_list_send_message_date);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    //위치 받아오기
                    int position = getAdapterPosition();

                    //클릭이벤트 설정하기
                    onItemClickEventListener.onItemClick(v, position);

                    Log.d("test","position = "+ getAdapterPosition());





                }
            });
        }
    }
}
