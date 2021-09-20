package com.kbc.Chatting;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kbc.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;


public class Chatting_Send_Activity extends AppCompatActivity {



    private EditText editText;

    private ArrayList<Chatting_Item> chatting_items = new ArrayList<>();
    private Chatting_Send_RecycleAdapter chatting_send_recycleAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private String storeManager_id;
    private TextView chatting_other_name;
    private  String click_chatting_list_name;

    private  String button_name;
    private String send_date, send_time;


    //데이터 베이스 관련
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private int chatting_number;
    private String login_id, chat_mode;


    public Chatting_Send_Activity(){}
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatting_send_activity);


        Intent intent = getIntent();
        chat_mode = intent.getExtras().getString("mode");

        click_chatting_list_name = intent.getExtras().getString("click_chatting_list_name");


        //채팅방 제목 -> 유저이름으로!!
        chatting_other_name = findViewById(R.id.other_userName);
        chatting_other_name.setText(click_chatting_list_name);

        chatting_number = intent.getExtras().getInt("chatting_number");

        Log.d(TAG, "채팅방 제목 -> " + click_chatting_list_name + " : 채팅방 번호 -> " + chatting_number );


        //파이어베이스 연동
        FirebaseConnector();

        //채팅 리스트 불러오기
        recyclerView = findViewById(R.id.chatrooms_recycleView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //어뎁터 적용
        chatting_send_recycleAdapter = new Chatting_Send_RecycleAdapter(chatting_items);
        chatting_send_recycleAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(chatting_send_recycleAdapter);



        //메세지 입력내용 받아오기
        editText = findViewById(R.id.chatting_input_text);
        Button send_btn = findViewById(R.id.send_message);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //메세지 보낸 시간
                Calendar calendar = Calendar.getInstance();
                DateFormat dateFormat_date = new SimpleDateFormat("yyyy년 MM월 dd일");
                DateFormat dateFormat_time = new SimpleDateFormat("HH:mm");

                send_date = dateFormat_date.format(calendar.getTime());
                send_time = dateFormat_time.format(calendar.getTime());


                //데이터베이스에 쓰기
                DatabaseReference insert_dbRef = databaseReference.child("id").child(login_id).child("chatting").child(chatting_number+"").child("input").
                        child("me").child(Get_Me_Message_Count());

                insert_dbRef.child(Chatting.DATE).setValue(send_date);
                insert_dbRef.child(Chatting.MESSAGE).setValue(editText.getText().toString());
                insert_dbRef.child(Chatting.NAME).setValue(login_id);
                insert_dbRef.child(Chatting.TIME).setValue(send_time);
                insert_dbRef.child(Chatting.PROFILEUTL).setValue("http://seohee");

                chatting_send_recycleAdapter.addItem(new Chatting_Item(login_id, "http://seohee", editText.getText().toString(), send_time, Chatting.RIGHT_CONTENT));



                //키패드 안보이게!
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

                editText.setText("");


                linearLayoutManager.scrollToPosition(chatting_send_recycleAdapter.getItemCount()-1);


            }
        });
    }

    //뒤로가기 이벤트
    public void click_back(View view){


          switch (view.getId()){
            //채팅방 나가기
              case R.id.chatting_close:
                //채팅하는곳 액티비티 닫아주고,
                  finish();
              break;
          }
    }



    //데베 객체 연결
    public void FirebaseConnector(){
        //테스트 데이터
        login_id = "seohee";

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

    private String[] send_chatting_me_date;
    private  String send_chatting_me_time;


    private Object[] send_chatting_me, send_chatting_other;
    private String current_chatting_date ="2021년";





    //정보 가져오기
    public void Read_All_Data(){

        me_message_count = other_message_count =1;
        current_chatting_date ="2021년";

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                map = (Map<String, Object>) dataSnapshot.getValue();

                if(map != null){
                    //아이디 별로 맵에 담기
                    for(String id: map.keySet()){
                        id_map = (Map<String, Object>) map.get(id);
                    }
//                    Log.d(TAG, "카카오아이디 : "+ id_map.keySet());

                    for(String id_inside : id_map.keySet())
                            id_inside_map = (Map<String, Object>) id_map.get(id_inside);

//                        Log.d(TAG, "아이디 내부 : "+ id_inside_map);

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
                        getChatting();

                }
                chatting_send_recycleAdapter.notifyDataSetChanged();
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
        input_map = chatting_arraylist.get(chatting_number);
        chatting_map = input_map.values().toArray();
        chatrooms_map = (Map<String, Object>) chatting_map[0];
        Log.d(TAG, "채팅 내역 상세   : " + chatrooms_map.keySet());

        chatting_me_arraylist = (ArrayList<HashMap<String, String>>) chatrooms_map.get("me");
        chatting_other_arraylist = (ArrayList<HashMap<String, String>>) chatrooms_map.get("other");
//
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

            Insert_Date_Order_Item(send_chatting_me, send_chatting_other);

        }

        while (me_message_count != chatting_me_arraylist.size()){
                Check_New_Date(send_chatting_me[date].toString());
                chatting_send_recycleAdapter.addItem(new Chatting_Item(send_chatting_me[name].toString(),send_chatting_me[profileUrl].toString(), send_chatting_me[message].toString(),send_chatting_me[time].toString(), Chatting.RIGHT_CONTENT));
                me_message_count++;

                if (me_message_count == chatting_me_arraylist.size())
                    break;

                send_chatting_me = chatting_me_arraylist.get(me_message_count).values().toArray();

        }

        while (other_message_count != chatting_other_arraylist.size()) {
            Check_New_Date(send_chatting_other[date].toString());
            chatting_send_recycleAdapter.addItem(new Chatting_Item(send_chatting_other[name].toString(), send_chatting_other[profileUrl].toString(), send_chatting_other[message].toString(), send_chatting_other[time].toString(), Chatting.LEFT_CONTENT));
            other_message_count++;

            if(other_message_count == chatting_other_arraylist.size())
                break;

            send_chatting_other = chatting_other_arraylist.get(other_message_count).values().toArray();

        }

    }



    //시간순으로 아이템 넣기
    private void Insert_Date_Order_Item(Object[] send_chatting_me, Object[] send_chatting_other){
        send_chatting_me_date = send_chatting_me[date].toString().split(" ");
        String[] send_chatting_other_date = send_chatting_other[date].toString().split(" ");

        send_chatting_me_time = send_chatting_me[time].toString();
        String send_chatting_other_time = send_chatting_other[time].toString();

        int insert_type = 0;
        insert_type = Compare_Date(send_chatting_me_date, send_chatting_other_date, send_chatting_me_time, send_chatting_other_time);

        switch (insert_type){
            case Chatting.ME:
                Check_New_Date(send_chatting_me[date].toString());
                Log.d(TAG, "들어가는 나의 메세지 1 -> " + send_chatting_me[message].toString());

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


    public String Get_Me_Message_Count(){
        return chatting_me_arraylist.size() + "";
    }

    private void Insert_Me_Message(){
        me_message_count++;
        send_chatting_me = chatting_me_arraylist.get(me_message_count).values().toArray();
        Check_New_Date(send_chatting_me[date].toString());
        chatting_send_recycleAdapter.addItem(new Chatting_Item(send_chatting_other[name].toString(), send_chatting_other[profileUrl].toString(), send_chatting_other[message].toString(), send_chatting_other[time].toString(), Chatting.LEFT_CONTENT));
    }


}

