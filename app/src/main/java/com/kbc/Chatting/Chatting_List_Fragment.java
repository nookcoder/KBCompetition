package com.kbc.Chatting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kbc.Personal_MainActivity;
import com.kbc.R;
import com.kbc.StoreManger.StoreManager_MainActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Chatting_List_Fragment extends Fragment implements
Chatting_List_RecycleAdapter.OnItemClickEventListener{

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private Chatting_List_RecycleAdapter chatting_list_recycleAdapter;

    private StoreManager_MainActivity storeManager_mainActivity;
    private Personal_MainActivity personal_mainActivity;


    private String userId;
    private Bundle bundle;


    //데베 관련 변수
    private ArrayList<Chatting_Item> chatting_items = new ArrayList<>();
    public static FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private String chat_mode;

    //페이지 불러올떄마다 추가
    @Override
    public void onStart() {
        super.onStart();
        chatting_list_recycleAdapter.notifyDataSetChanged();
    }

    //뷰 그룹 반환 + 생성자 개념
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootViewGroup =(ViewGroup) inflater.inflate(R.layout.chatting_list, container, false);


        bundle = getArguments();
        if(bundle != null){
            userId = bundle.getString("userID");
            chat_mode = bundle.getString("mode");
        }
        Log.d("채팅 리스트 프래그먼 아이디 : ",userId);
        //RecycleView 연결
        recyclerView =rootViewGroup.findViewById(R.id.chatting_list_recycleView);

        //액티비티 가져오고,
        if(chat_mode.equals(Chatting.STORE_MANAGER)){
            storeManager_mainActivity = (StoreManager_MainActivity)getActivity();
            linearLayoutManager = new LinearLayoutManager(storeManager_mainActivity);
        }
        else if (chat_mode.equals(Chatting.PERSONAL)){
            personal_mainActivity = (Personal_MainActivity)getActivity();
            linearLayoutManager = new LinearLayoutManager(personal_mainActivity);
        }

        recyclerView.setLayoutManager(linearLayoutManager);
        //RecycleView에 사용 Adapter생성
        //클릭 이벤트 연결
        chatting_list_recycleAdapter = new Chatting_List_RecycleAdapter(chatting_items, this);
        recyclerView.setAdapter(chatting_list_recycleAdapter);


        Log.d(TAG,chatting_list_recycleAdapter.getItemCount() +"");

        for(int index = 0 ; index < chatting_list_recycleAdapter.getItemCount() ; index++)
            Log.d(TAG, "어뎁터 이름 -> " + chatting_list_recycleAdapter.getItemName(index));

        return rootViewGroup;
    }

    @Override
    public void onResume() {
        super.onResume();
        chatting_list_recycleAdapter.removeAll();
        FirebaseConnector();
        Get_Database();
    }

    @Override
    public void onItemClick(View view, int position) {
        Chatting_List_RecycleAdapter.ViewHolder viewHolder = (Chatting_List_RecycleAdapter.ViewHolder)recyclerView
                .findViewHolderForAdapterPosition(position);

        //선택한 채팅방 이름 (상대방)
        String click_chatting_list_name = viewHolder.chatting_list_name.getText().toString();
        //선택한 채팅방 번호
        int chatting_number = position +1;

        //채팅방들어가기
        Intent intent = new Intent(getActivity(), Chatting_Send_Activity.class );
        intent.putExtra("click_chatting_list_name",click_chatting_list_name);
        intent.putExtra("userID", userId);
        //모드 구분해주기
        if(chat_mode.equals(Chatting.STORE_MANAGER))
            intent.putExtra("mode", Chatting.STORE_MANAGER);
        else
            intent.putExtra("mode", Chatting.PERSONAL);

        intent.putExtra("chatting_number", chatting_number);

        startActivity(intent);


    }

    public void FirebaseConnector(){

        switch (chat_mode){
            case Chatting.PERSONAL:
                FirebaseApp.initializeApp(storeManager_mainActivity);
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Personal");
                break;

            case Chatting.STORE_MANAGER:
                FirebaseApp.initializeApp(personal_mainActivity);
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("StoreManager");
                break;
        }
    }

    //전체 트리 가져오고, 모드 나눠서 세부 id로 들어가기
    private Map<String, Object> map;
    private Map<String, Object> id_map;
    private Map<String, Object> id_inside_map;

    private Map<String, Object> chatrooms_map;
    //해당 id의 채팅방 + 채팅 내용 불러오기!
    private ArrayList<HashMap<String, String>> chatrooms_arraylist, chatting_arraylist;
    private ArrayList<HashMap<String, String>> chatting_me_arraylist, chatting_other_arraylist;
    private Object[] chatting_me_last_message ,chatting_other_last_message;

    private HashMap<String, String> input_map;
    private Object[] chatting_map;

    private int name, profileUrl, message, time,date;
    private  int me_message_count = 1 , other_message_count = 1;

    private Chatting_Send_Activity chatting_send_activity = new Chatting_Send_Activity();
    private int chatrooms_count ;
    private Chatting_Item chatting_last_item = new Chatting_Item();



    public void Get_Database(){

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                map = (Map<String, Object>)dataSnapshot.getValue();

                if(map != null){
                    for(String id: map.keySet())
                        id_map = (Map<String, Object>)map.get(id);

                    int check_id_count = 0;
                    for(String id_inside : id_map.keySet()){

                        if(id_inside.equals(userId)){
                            id_inside_map = (Map<String, Object>)id_map.get(id_inside);
                            break;
                        }
                        check_id_count++;
                    }

                    if(check_id_count == id_map.size()){
                        Initialize_Id();
                    }
                    else {
                        for(String in_inside_key : id_inside_map.keySet()){
                            switch (in_inside_key){
                                //아이디 -> 채팅 내역 리스트 담기
                                case "chatrooms":
                                    chatrooms_arraylist = (ArrayList<HashMap<String, String>>)id_inside_map.get(in_inside_key);
                                    break;

                                case "chatting":
                                    chatting_arraylist = (ArrayList<HashMap<String, String>>)id_inside_map.get(in_inside_key);
                                    break;
                            }
                        }
                        Get_Last_Chatting_Information();
                    }
                }

                chatting_list_recycleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    //처음 채팅 아이디 넣기
    private void Initialize_Id(){

        databaseReference.child("id").child(userId).child("chatrooms").child("0").setValue("채팅방없음");
        databaseReference.child("id").child(userId).child("chatting").child("0").setValue("채팅방없음");

    }

    private void Get_Last_Chatting_Information(){
        //채팅방 개수
        chatrooms_count = chatting_arraylist.size()-1;

        Log.d("채팅 리스트", chatrooms_arraylist.toString());


        if(chatting_arraylist.size() != 1){
            input_map = chatting_arraylist.get(1);
            chatting_map = input_map.values().toArray();
            chatrooms_map = (Map<String, Object>) chatting_map[0];

            chatting_me_arraylist = (ArrayList<HashMap<String, String>>) chatrooms_map.get("me");
            chatting_other_arraylist = (ArrayList<HashMap<String, String>>) chatrooms_map.get("other");

            //키 인덱스 찾기
            Object[] send_key = chatting_me_arraylist.get(0).keySet().toArray();
            for (int key_index = 0; key_index < send_key.length; key_index++) {
                if (send_key[key_index].toString().equals(Chatting.NAME))
                    name = key_index;

                if (send_key[key_index].toString().equals(Chatting.PROFILEUTL))
                    profileUrl = key_index;

                if (send_key[key_index].toString().equals(Chatting.MESSAGE))
                    message = key_index;

                if (send_key[key_index].toString().equals(Chatting.TIME))
                    time = key_index;

                if (send_key[key_index].toString().equals(Chatting.DATE))
                    date = key_index;
            }

            for(int position=0; position< chatrooms_count ; position++)
                Insert_Chatroom_Ui(position);

        }

    }

    private void Insert_Chatroom_Ui(int position) {
        String last_name, last_profileUrl, last_message = "", last_time = "", last_date = "";

        input_map = chatting_arraylist.get(position + 1);
        chatting_map = input_map.values().toArray();
        chatrooms_map = (Map<String, Object>) chatting_map[0];

        chatting_me_arraylist = (ArrayList<HashMap<String, String>>) chatrooms_map.get("me");
        chatting_other_arraylist = (ArrayList<HashMap<String, String>>) chatrooms_map.get("other");

        //마지막 채팅 정보 나, 상대방 각각 가져오기
        chatting_me_last_message = chatting_me_arraylist.get(chatting_me_arraylist.size() - 1).values().toArray();
        chatting_other_last_message = chatting_other_arraylist.get(chatting_other_arraylist.size() - 1).values().toArray();

        Log.d("나의 채팅수", chatting_me_arraylist.size() + "");
        Log.d("상대의 채팅수", chatting_other_arraylist.size() + "");

        //프로필사진, 이름은 상대방으로 되어있어야 함!
        last_profileUrl = chatting_other_last_message[profileUrl].toString();
        last_name = chatting_other_last_message[name].toString();

        if (chatting_other_arraylist.size() != 1 && chatting_me_arraylist.size() != 1) {
            int last_message_mode = chatting_send_activity.Compare_Date(
                    chatting_me_last_message[date].toString().split(" "), chatting_other_last_message[date].toString().split(" "),
                    chatting_me_last_message[time].toString(), chatting_other_last_message[time].toString());

            switch (last_message_mode) {
                //내 시간이 더 빠르면 상대방이 마지막
                case Chatting.ME:
                    last_date = chatting_other_last_message[date].toString();
                    last_message = chatting_other_last_message[message].toString();
                    last_time = chatting_other_last_message[time].toString();
                    break;

                //상대방이 더빠르면 내가 마지막
                case Chatting.OTHER:
                    last_date = chatting_me_last_message[date].toString();
                    last_message = chatting_me_last_message[message].toString();
                    last_time = chatting_me_last_message[time].toString();
                    break;
            }
        } else if (chatting_other_arraylist.size() == 1 && chatting_me_arraylist.size() > 1) {
            last_date = chatting_me_last_message[date].toString();
            last_message = chatting_me_last_message[message].toString();
            last_time = chatting_me_last_message[time].toString();
        } else if (chatting_me_arraylist.size() == 1 && chatting_other_arraylist.size() > 1) {
            last_date = chatting_other_last_message[date].toString();
            last_message = chatting_other_last_message[message].toString();
            last_time = chatting_other_last_message[time].toString();


        }
        Insert_Chatroom_DB(position + 1, last_name, last_date, last_message, last_profileUrl, last_time);
        chatting_list_recycleAdapter.addItem(new Chatting_Item(last_name, last_profileUrl, last_message, last_time, last_date));


    }


    //데베에 넣기
    private void Insert_Chatroom_DB(int chatrooms_count, String name, String date, String message, String profileUrl, String time){

        DatabaseReference chatroom_db =databaseReference.child("id").child(userId).child("chatrooms").child(chatrooms_count+"");

        chatroom_db.child(Chatting.NAME).setValue(name);
        chatroom_db.child(Chatting.DATE).setValue(date);
        chatroom_db.child(Chatting.MESSAGE).setValue(message);
        chatroom_db.child(Chatting.PROFILEUTL).setValue(profileUrl);
        chatroom_db.child(Chatting.TIME).setValue(time);

    }
}



