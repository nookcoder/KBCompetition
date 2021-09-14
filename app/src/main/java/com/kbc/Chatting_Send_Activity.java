package com.kbc;


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
import com.kbc.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import static android.content.ContentValues.TAG;


public class Chatting_Send_Activity extends AppCompatActivity {

    private EditText editText;
    private ListView listView;
    private ArrayList<Chatting_Item> chatting_items = new ArrayList<>();
    private ChattingAdapter chattingAdapter;
    private FirebaseConnector dbconnector;
    private String storeManager_id;
    private TextView chatting_other_name;
    private  String button_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatting_send_activity);

        Intent intent = getIntent();
        String click_chatting_list_name = intent.getExtras().getString("click_chatting_list_name");



        //채팅방 제목 -> 유저이름으로!!
        chatting_other_name = findViewById(R.id.other_userName);
        chatting_other_name.setText(click_chatting_list_name);

        //메세지 입력내용 받아오기
        editText = findViewById(R.id.chatting_input_text);
        //채팅 리스트 불러오기
        listView = findViewById(R.id.chatting_listview);
        //어뎁터 적용
        chattingAdapter = new ChattingAdapter(chatting_items, getLayoutInflater());
        listView.setAdapter(chattingAdapter);



        Button send_btn = findViewById(R.id.send_message);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //데이터베이스에 쓰기
//                databaseReference.child("넹~").push().setValue("연결중입니다~");
                Log.d("클릭","들어가!!!");
            }
        });

//        //db객체 만들어주고,
//        dbconnector = FirebaseConnector.getInstance(this, "StoreManager",storeManager_id);
//
//        //채팅 업데이트 계속 해주기!!
//        dbconnector.Update_chatting_listView(chatting_items,chattingAdapter,listView);
//


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
//
//        //파이어베이스에 저장할 유저이름, 메세지, 프로필 이미지
//        String userName = Chatting.userName;
//        String input_text = editText.getText().toString();
//        String profileUrl = Chatting.profileUrl;
//        //메세지 보낸 시간
//        Calendar calendar = Calendar.getInstance();
//        String send_message_time = calendar.get(Calendar.HOUR_OF_DAY)+ ":" + calendar.get(Calendar.MINUTE);
//
//        //chattingitem객체 설정
//        Chatting_Item new_chatting_item = new Chatting_Item(userName,profileUrl, input_text,send_message_time);
//
//
//        //키패드 안보이게!
//        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);


    }
}