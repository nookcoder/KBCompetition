package com.kbc;

import android.app.Activity;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.ChildEventListener;
import com.kbc.Chatting.Chatting;
import com.kbc.Chatting.ChattingAdapter;
import com.kbc.Chatting.Chatting_Item;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class FirebaseConnector {

    //싱글톤
    private static FirebaseConnector firebaseConnector = null;

    //데이터베이스 연결 변수
    private static FirebaseDatabase firebaseDatabase;
    private static DatabaseReference databaseReference;
    private String login_id, chat_mode;

    //액티비티에 연결 객체 생성
    public static FirebaseConnector getInstance(Activity activity, String mode, String login_id,
                                                String chat_mode) {

        firebaseConnector = new FirebaseConnector(activity, mode, login_id,chat_mode);

        return firebaseConnector;
    }

    public static FirebaseConnector getInstance(){
        return firebaseConnector;
    }

    //데베 구축
    private FirebaseConnector(Activity activity, String mode, String login_id,
                              String chat_mode){
        this.chat_mode = chat_mode;
        FirebaseApp.initializeApp(activity);
        firebaseDatabase = FirebaseDatabase.getInstance();

        //유저모드, 점주모드로 나눠서 dbRef 객체 생성
        switch (mode){
            case "Person":
                databaseReference = firebaseDatabase.getReference("Person");
                break;

            case "StoreManager":
                databaseReference = firebaseDatabase.getReference("StoreManager");

                break;

        }

        this.login_id = login_id;

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



    //정보 가져오기
    private void Read_All_Data(){

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
                        Initialize_Id();
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



    private void getChatting(){
        //채팅내역 불러오기 코드
        input_map = chatting_arraylist.get(1);
        chatting_map = input_map.values().toArray();
        chatrooms_map = (Map<String, Object>)chatting_map[0];
        Log.d(TAG, "채팅 내역 상세   : " + chatrooms_map.keySet());

        chatting_me_arraylist = (ArrayList<HashMap<String, String>>)chatrooms_map.get("me");
        chatting_other_arraylist = (ArrayList<HashMap<String, String>>)chatrooms_map.get("other");

//        Log.d(TAG, "나의 채팅 내역 : " + chatting_me_arraylist);
//        Log.d(TAG, "나의 채팅 기록 갯수 : " + chatting_me_arraylist.get(1));
//        Log.d(TAG, "상대방의 채팅 내역 : " + chatting_other_arraylist);

        HashMap<String, String>first_send = chatting_me_arraylist.get(1);
        Log.d(TAG, "첫 번째 메세지  : " + first_send.keySet());
        Log.d(TAG, "첫 번째 메세지  : " + first_send.values());

        Object[] first_send_me = first_send.values().toArray();
        Log.d(TAG, "나의 메세지 내용 " + first_send_me.length);
        Log.d(TAG, "나의 메세지 내용 " + first_send_me.toString());
        Log.d(TAG, "나의 메세지 내용 " + first_send_me[0]);




    }

    public ArrayList<HashMap<String, String>> Get_Chatting_Me_Message(){
        return chatting_me_arraylist;
    }

    //처음 채팅 아이디 넣기
    private void Initialize_Id(){

        databaseReference.child("id").child(login_id).child("chatrooms").child("0").setValue("채팅방없음");
        databaseReference.child("id").child(login_id).child("chatting").child("0").setValue("채팅방없음");

    }

    public String getKaKao_id(){
        if(id_map != null){
            return id_map.keySet().toString();
        }
        return Chatting.NO_CHATTING_ROOM;
    }

    //채팅방 업데이트
    public void Update_chatrooms(String name, String profileUrl, String message, String time){

        databaseReference.child("id").child(login_id).child("chatrooms").child("0").child("message").setValue(name);
        databaseReference.child("id").child(login_id).child("chatrooms").child("0").child("name").setValue(profileUrl);
        databaseReference.child("id").child(login_id).child("chatrooms").child("0").child("profileUrl").setValue(message);
        databaseReference.child("id").child(login_id).child("chatrooms").child("0").child("time").setValue(time);

    }

    public void Update_chatting_listView( ArrayList<Chatting_Item> chatting_items,
                                          ChattingAdapter chattingAdapter,
                                          ListView listView){
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                //새로 추가된 채팅 입력 가져오기
                Chatting_Item chatting_item = snapshot.getValue(Chatting_Item.class);
                //데이터 추가
                chatting_items.add(chatting_item);
                //리스트뷰 갱신
                chattingAdapter.notifyDataSetChanged();
                //리스트뷰 마지막 위치로 스크롤 이동
                listView.setSelection(chatting_items.size()-1);

            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }







}
