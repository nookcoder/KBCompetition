package com.kbc.Chatting;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kbc.Chatting.Chatting;
import com.kbc.Chatting.ChattingAdapter;
import com.kbc.Chatting.Chatting_Item;
import com.kbc.Chatting.Chatting_List_Fragment;
import com.kbc.FirebaseConnector;
import com.kbc.R;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;


public class Chatting_Send_Activity extends AppCompatActivity {


    private EditText editText;

    private static ArrayList<Chatting_Item> chatting_items = new ArrayList<>();
    private Chatting_Send_RecycleAdapter chatting_send_recycleAdapter;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private FirebaseConnector dbconnector;
    private String storeManager_id;
    private TextView chatting_other_name;
    private  String click_chatting_list_name;

    private  String button_name;


    private String login_id, chat_mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatting_send_activity);


//        Intent intent = getIntent);
//        chat_mode = intent.getExtras().getString("mode");

////        String click_chatting_list_name = intent.getExtras().getString("click_chatting_list_name");
////
////
//
//        chatting_items.add(new Chatting_Item(click_chatting_list_name, "FF","채팅어렵다아아아ㅏ유ㅠㅠㅠ", "01:12",Chatting.LEFT_CONTENT));
        //파이어베이스 연동
        FirebaseConnector();

        //채팅 리스트 불러오기
        recyclerView = findViewById(R.id.chatrooms_recycleView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //어뎁터 적용
        chatting_send_recycleAdapter = new Chatting_Send_RecycleAdapter(chatting_items);
        recyclerView.setAdapter(chatting_send_recycleAdapter);



        //메세지 입력내용 받아오기
        editText = findViewById(R.id.chatting_input_text);

        Button send_btn = findViewById(R.id.send_message);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //데이터베이스에 쓰기
//                databaseReference.child("넹~").push().setValue("연결중입니다~");

                Log.d(TAG, "채팅 리스트 -> "+ chatting_send_recycleAdapter.getItemCount());

            }
        });
        chatting_send_recycleAdapter.notifyDataSetChanged();


    }

    //뒤로가기 이벤트
    public void click_back(View view){
          switch (button_name){

            //채팅방 나가기
            case "chatting_close":
                //채팅하는곳 액티비티 닫아주고,
                finish();
                //채팅방 목록 리스트 열어주기!
                Intent chatting_list_intent = new Intent(this, Chatting_List_Fragment.class);
                startActivity(chatting_list_intent);
                break;

        }
    }

    public void clickSend(View view){

        //파이어베이스에 저장할 유저이름, 메세지, 프로필 이미지
        String userName = Chatting.userName;
        String input_text = editText.getText().toString();
        String profileUrl = Chatting.profileUrl;

        //메세지 보낸 시간
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH : mm");
        dateFormat.format(calendar.getTime());



        //키패드 안보이게!
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);


    }



    //데이터 베이스 관련
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;


    //데베 객체 연결
    public void FirebaseConnector(){
        //테스트 데이터
        login_id = "seohee";
        chat_mode = Chatting.STORE_MANAGER_MODE;

        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();

        switch (chat_mode){
            case "Person":
                databaseReference = firebaseDatabase.getReference("Person");
                break;

            case "StoreManager":
                databaseReference = firebaseDatabase.getReference("StoreManager");
                break;
        }
        Read_All_Data();
    }

    //전체 트리 가져오고, 모드 나눠서 세부 id로 들어가기
    private Map<String, Object> map;
    private Map<String, Object> id_map;
    private Map<String, Object> id_inside_map;

    private HashMap<String, String> input_map;
    private Object[] chatting_map;

    private Map<String, Object> chatrooms_map;
    //해당 id의 채팅방 + 채팅 내용 불러오기!
    private ArrayList<HashMap<String, String>> chatrooms_arraylist, chatting_arraylist;
    private ArrayList<HashMap<String, String>> chatting_me_arraylist, chatting_other_arraylist;

    private int name, profileUrl, message, time,date;
    private  int me_message_count = 1 , other_message_count = 1;

    private Object[] send_chatting_me, send_chatting_other;
    private String current_chatting_date ="2021년";





    //정보 가져오기
    public void Read_All_Data(){

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                map = (Map<String, Object>) dataSnapshot.getValue();

                if(map != null){
                    //아이디 별로 맵에 담기
                    for(String id: map.keySet()){
                        id_map = (Map<String, Object>) map.get(id);
                    }

                    Log.d(TAG, "카카오아이디 : "+ id_map.keySet());

                    int check_id_count = 0;
                    for(String id_inside : id_map.keySet()){

                        //로그인 된 아이디의 데이터를 가져오기
                        if(id_inside.equals(login_id)){
                            id_inside_map = (Map<String, Object>) id_map.get(id_inside);
                            break;
                        }
                        check_id_count++;
                    }

                    //등록된적없는 id
                    if( check_id_count == id_map.size()){
//                        Initialize_Id();
                    }
                    //등록된 적 있는 id
                    else{

                        Log.d(TAG, "아이디 내부 : "+ id_inside_map);

                        for(String in_inside_key: id_inside_map.keySet()){
                            switch (in_inside_key){
                                //아이디 -> 채팅방 리스트에 담기
                                case "chatrooms":

                                    chatrooms_arraylist = (ArrayList<HashMap<String, String>>)id_inside_map.get(in_inside_key);

                                    break;
                                //아이디 -> 채팅 내역 리스트에 담기
                                case "chatting":

                                    chatting_arraylist = (ArrayList<HashMap<String, String>>)id_inside_map.get(in_inside_key);
                                    break;
                            }
                        }

                        Log.d(TAG, "채팅방 내역 : " + chatrooms_arraylist);
                        Log.d(TAG, "채팅방 내용 : " + chatting_arraylist);
                        getChatting();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


    //채팅내역 불러오기
    private void getChatting() {
        input_map = chatting_arraylist.get(1);
        chatting_map = input_map.values().toArray();
        chatrooms_map = (Map<String, Object>) chatting_map[0];
        Log.d(TAG, "채팅 내역 상세   : " + chatrooms_map.keySet());

        chatting_me_arraylist = (ArrayList<HashMap<String, String>>) chatrooms_map.get("me");
        chatting_other_arraylist = (ArrayList<HashMap<String, String>>) chatrooms_map.get("other");

        Log.d(TAG, "나의 채팅 내역 : " + chatting_me_arraylist);
        Log.d(TAG, "나의 채팅 기록 갯수 : " + chatting_me_arraylist.get(1));
        Log.d(TAG, "나의 채팅 기록 갯수 : " + chatting_me_arraylist.get(1).values());

        //키 인덱스 찾기
        Object[] send_key = chatting_me_arraylist.get(1).keySet().toArray();
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



        while(true){
            if(me_message_count == chatting_me_arraylist.size() ||
            other_message_count == chatting_other_arraylist.size())
                break;

            send_chatting_me = chatting_me_arraylist.get(me_message_count).values().toArray();
            send_chatting_other = chatting_other_arraylist.get(other_message_count).values().toArray();

            //채팅방 제목 -> 유저이름으로!!
            chatting_other_name = findViewById(R.id.other_userName);
            chatting_other_name.setText(send_chatting_other[name].toString());

            Insert_Date_Order_Item(send_chatting_me, send_chatting_other);

        }
//        if(me_message_count != chatting_me_arraylist.size() && other_message_count == chatting_other_arraylist.size()) {
//            Check_New_Date(send_chatting_me[date].toString());
//            chatting_send_recycleAdapter.addItem(new Chatting_Item(send_chatting_me[name].toString(),send_chatting_me[profileUrl].toString(), send_chatting_me[message].toString(),send_chatting_me[time].toString(), Chatting.RIGHT_CONTENT));
//        }
//        else if (me_message_count == chatting_me_arraylist.size() && other_message_count != chatting_other_arraylist.size()) {
//
//            Check_New_Date(send_chatting_other[date].toString());
//            chatting_send_recycleAdapter.addItem(new Chatting_Item(send_chatting_other[name].toString(), send_chatting_other[profileUrl].toString(), send_chatting_other[message].toString(), send_chatting_other[time].toString(), Chatting.LEFT_CONTENT));
//        }
//        else
        if (me_message_count != chatting_me_arraylist.size()){
            Check_New_Date(send_chatting_me[date].toString());
            chatting_send_recycleAdapter.addItem(new Chatting_Item(send_chatting_me[name].toString(),send_chatting_me[profileUrl].toString(), send_chatting_me[message].toString(),send_chatting_me[time].toString(), Chatting.RIGHT_CONTENT));
        }
        else{
            Check_New_Date(send_chatting_other[date].toString());
            chatting_send_recycleAdapter.addItem(new Chatting_Item(send_chatting_other[name].toString(), send_chatting_other[profileUrl].toString(), send_chatting_other[message].toString(), send_chatting_other[time].toString(), Chatting.LEFT_CONTENT));

        }
        chatting_send_recycleAdapter.notifyDataSetChanged();

    }


   //처음 채팅 아이디 넣기
    private void Initialize_Id(){

        databaseReference.child("id").child(login_id).child("chatrooms").child("0").setValue("채팅방없음");
        databaseReference.child("id").child(login_id).child("chatting").child("0").setValue("채팅방없음");

    }

    //시간순으로 아이템 넣기
    private void Insert_Date_Order_Item(Object[] send_chatting_me, Object[] send_chatting_other){
        String[] send_chatting_me_date = send_chatting_me[date].toString().split(" ");
        String[] send_chatting_other_date = send_chatting_other[date].toString().split(" ");

        String send_chatting_me_time = send_chatting_me[time].toString();
        String send_chatting_other_time = send_chatting_other[time].toString();

        int insert_type = 0;
        insert_type = Compare_Date(send_chatting_me_date, send_chatting_other_date, send_chatting_me_time, send_chatting_other_time);

        switch (insert_type){
            case Chatting.ME:
                Check_New_Date(send_chatting_me[date].toString());
                chatting_send_recycleAdapter.addItem(new Chatting_Item(send_chatting_me[name].toString(),send_chatting_me[profileUrl].toString(), send_chatting_me[message].toString(),send_chatting_me[time].toString(), Chatting.RIGHT_CONTENT));
                me_message_count++;
                break;

            case Chatting.OTHER:
                Check_New_Date(send_chatting_other[date].toString());
                chatting_send_recycleAdapter.addItem(new Chatting_Item(send_chatting_other[name].toString(), send_chatting_other[profileUrl].toString(), send_chatting_other[message].toString(), send_chatting_other[time].toString(), Chatting.LEFT_CONTENT));
                other_message_count++;
                break;
        }



    }

    //시간 비교하기
    public int Compare_Date(String[] send_chatting_me_date, String[] send_chatting_other_date, String send_chatting_me_time, String send_chatting_other_time){
        //년, 월, 일 비교하고 -> 다 같으면 시간비교하기
        if(send_chatting_me_date[0].compareTo(send_chatting_other_date[0]) < 0)
            return Chatting.ME;

        else if (send_chatting_me_date[0].compareTo(send_chatting_other_date[0]) > 0)
            return Chatting.OTHER;

        else {

            if(send_chatting_me_date[1].compareTo(send_chatting_other_date[1]) < 0)
                return Chatting.ME;

            else if (send_chatting_me_date[1].compareTo(send_chatting_other_date[1]) > 0)
                return Chatting.OTHER;

            else{

                if(send_chatting_me_date[2].compareTo(send_chatting_other_date[2]) < 0)
                    return Chatting.ME;

                else {
                        if(send_chatting_me_time.compareTo(send_chatting_other_time) < 0)
                            return Chatting.ME;
                        else
                            return Chatting.OTHER;
                }
            }
        }
    }


    public void Check_New_Date(String insert_date){
        if (current_chatting_date.compareTo(insert_date) < 0){
             chatting_send_recycleAdapter.addItem(new Chatting_Item(insert_date,Chatting.CENTER_CONTENT) );
            current_chatting_date = insert_date;

        }

    }

}

