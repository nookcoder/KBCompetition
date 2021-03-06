package com.kbc.Chatting;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.kbc.Common.Creating;
import com.kbc.Personal_Purchase_Fragment;
import com.kbc.Pickup.Pickup_Item;
import com.kbc.R;
import com.kbc.Sale.Sale_Item;
import com.kbc.Sale.StoreManager_SalesList_Fragment;
import com.kbc.Server.Merchant;
import com.kbc.Server.Personal;
import com.kbc.Server.PickUpData;
import com.kbc.Server.RetrofitBulider;
import com.kbc.Server.ServiceApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

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


    //????????? ????????? ??????
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private int chatting_number = 1, other_chatting_number = 1;
    static int chatting_message_count;
    private String userId, chat_mode;

    //???????????? ????????????
    String me_nick_name , other_nick_name;
    String personal_NickName, storeManager_NickName;



    public Chatting_Send_Activity(){}
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatting_send_activity);


        Intent intent = getIntent();
        //????????? ??????
        chat_mode = intent.getExtras().getString("mode");
        // ???????????????
        click_chatting_list_name = intent.getExtras().getString("click_chatting_list_name");
        // ??? ?????????
        userId = intent.getStringExtra("userID");


        switch (chat_mode){
            case Chatting.PERSONAL:
                get_personal_NickName(userId);
                get_storeManager_NickName(click_chatting_list_name);
                break;

            case Chatting.STORE_MANAGER:
                get_personal_NickName(click_chatting_list_name);
                get_storeManager_NickName(userId);
                break;
        }

        chatting_number = Find_Chatting_Number(chat_mode);
        //????????? ??????
        Log.d("??? ?????????", userId);
        other_chatting_number = Get_Other_Communication_Chatting();
        Log.d("???????????? ?????? ?????? ?????? ??????", other_chatting_number+"");



        //?????????????????? ??????
        FirebaseConnector();


        // ?????? ????????? ?????? ??????
        chatting_number = Find_Chatting_Number(chat_mode);

        Log.d("?????? ????????? ??????", chatting_number+"");


        //?????? ????????? ????????????
        recyclerView = findViewById(R.id.chatrooms_recycleView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //????????? ??????
        chatting_send_recycleAdapter = new Chatting_Send_RecycleAdapter(chatting_items);
        chatting_send_recycleAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(chatting_send_recycleAdapter);


        //????????? ???????????? ????????????
        editText = findViewById(R.id.chatting_input_text);
        Button send_btn = findViewById(R.id.send_message);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //????????? ?????? ??????
                Calendar calendar = Calendar.getInstance();
                DateFormat dateFormat_date = new SimpleDateFormat("yyyy??? MM??? dd???");
                DateFormat dateFormat_time = new SimpleDateFormat("HH:mm");

                send_date = dateFormat_date.format(calendar.getTime());
                send_time = dateFormat_time.format(calendar.getTime());


                Log.d("???????????? ??? " , chatting_message_count+ "");

                if(chatting_message_count == 0){
//                    databaseReference.child("id").child(userId).child("chatting").child(chatting_number+"").child("input").
//                            child("me").child(chatting_message_count+"").setValue("???????????????");

                    databaseReference.child("id").child(userId).child("chatting").child(chatting_number+"").child("input").
                            child("me").child(chatting_message_count+"").child(Chatting.DATE).setValue("0");
                    databaseReference.child("id").child(userId).child("chatting").child(chatting_number+"").child("input").
                            child("me").child(chatting_message_count+"").child(Chatting.MESSAGE).setValue("0");
                    databaseReference.child("id").child(userId).child("chatting").child(chatting_number+"").child("input").
                            child("me").child(chatting_message_count+"").child(Chatting.NAME).setValue(userId);
                    databaseReference.child("id").child(userId).child("chatting").child(chatting_number+"").child("input").
                            child("me").child(chatting_message_count+"").child(Chatting.TIME).setValue("0");
                    databaseReference.child("id").child(userId).child("chatting").child(chatting_number+"").child("input").
                            child("me").child(chatting_message_count+"").child(Chatting.PROFILEUTL).setValue("http://seohee");

                    //?????? ???????????? -> ?????? ?????????
                    DatabaseReference insert_other_init = databaseReference.child("id").child(userId).child("chatting").child(chatting_number+"").child("input").
                            child("other").child(chatting_message_count+"");

                    insert_other_init.child(Chatting.DATE).setValue("0");
                    insert_other_init.child(Chatting.MESSAGE).setValue("0");
                    insert_other_init.child(Chatting.NAME).setValue(click_chatting_list_name);
                    insert_other_init.child(Chatting.TIME).setValue("0");
                    insert_other_init.child(Chatting.PROFILEUTL).setValue("http://seohee");

                    DatabaseReference insert_other_db;

                    if(chat_mode.equals(Chatting.PERSONAL)){
                        Log.d("????????? ?????????", click_chatting_list_name);

                        insert_other_db = FirebaseDatabase.getInstance().getReference(Chatting.STORE_MANAGER).child("id").child(click_chatting_list_name).child("chatting").child(other_chatting_number+"")
                                .child("input").child("other").child(chatting_message_count+"");

                        insert_other_db.child(Chatting.DATE).setValue("0");
                        insert_other_db.child(Chatting.MESSAGE).setValue("0");
                        insert_other_db.child(Chatting.NAME).setValue(userId);
                        insert_other_db.child(Chatting.TIME).setValue("0");
                        insert_other_db.child(Chatting.PROFILEUTL).setValue("http://seohee");

                        insert_other_db = FirebaseDatabase.getInstance().getReference(Chatting.STORE_MANAGER).child("id").child(click_chatting_list_name).child("chatting").child(other_chatting_number+"")
                                .child("input").child("me").child("0");

                        insert_other_db.child(Chatting.DATE).setValue("0");
                        insert_other_db.child(Chatting.MESSAGE).setValue("0");
                        insert_other_db.child(Chatting.NAME).setValue(click_chatting_list_name);
                        insert_other_db.child(Chatting.TIME).setValue("0");
                        insert_other_db.child(Chatting.PROFILEUTL).setValue("http://seohee");
                    }
                    else {
                        insert_other_db = FirebaseDatabase.getInstance().getReference(Chatting.PERSONAL).child("id").child(click_chatting_list_name).child("chatting").child(other_chatting_number+"")
                                .child("input").child("other").child(chatting_message_count+"");


                        insert_other_db.child(Chatting.DATE).setValue("0");
                        insert_other_db.child(Chatting.MESSAGE).setValue("0");
                        insert_other_db.child(Chatting.NAME).setValue(userId);
                        insert_other_db.child(Chatting.TIME).setValue("0");
                        insert_other_db.child(Chatting.PROFILEUTL).setValue("http://seohee");

                        insert_other_db = FirebaseDatabase.getInstance().getReference(Chatting.PERSONAL).child("id").child(click_chatting_list_name).child("chatting").child(other_chatting_number+"")
                                .child("input").child("me").child("0");

                        insert_other_db.child(Chatting.DATE).setValue("0");
                        insert_other_db.child(Chatting.MESSAGE).setValue("0");
                        insert_other_db.child(Chatting.NAME).setValue(click_chatting_list_name);
                        insert_other_db.child(Chatting.TIME).setValue("0");
                        insert_other_db.child(Chatting.PROFILEUTL).setValue("http://seohee");
                    }

                    chatting_message_count++;


                }
                //????????????????????? ??????
                DatabaseReference insert_dbRef = databaseReference.child("id").child(userId).child("chatting").child(chatting_number+"")
                        .child("input").child("me").child(chatting_message_count+"");

                insert_dbRef.child(Chatting.DATE).setValue(send_date);
                insert_dbRef.child(Chatting.MESSAGE).setValue(editText.getText().toString());
                insert_dbRef.child(Chatting.NAME).setValue(userId);
                insert_dbRef.child(Chatting.TIME).setValue(send_time);
                insert_dbRef.child(Chatting.PROFILEUTL).setValue("http://seohee");


                DatabaseReference insert_other_db;

                if(chat_mode.equals(Chatting.PERSONAL)){
                    insert_other_db = FirebaseDatabase.getInstance().getReference(Chatting.STORE_MANAGER).child("id").child(click_chatting_list_name).child("chatting").child(other_chatting_number+"")
                            .child("input").child("other").child(chatting_message_count+"");
                }
                else {
                    insert_other_db = FirebaseDatabase.getInstance().getReference(Chatting.PERSONAL).child("id").child(click_chatting_list_name).child("chatting").child(other_chatting_number+"")
                            .child("input").child("other").child(chatting_message_count+"");
                }

                insert_other_db.child(Chatting.DATE).setValue(send_date);
                insert_other_db.child(Chatting.MESSAGE).setValue(editText.getText().toString());
                insert_other_db.child(Chatting.NAME).setValue(userId);
                insert_other_db.child(Chatting.TIME).setValue(send_time);
                insert_other_db.child(Chatting.PROFILEUTL).setValue("http://seohee");

                chatting_send_recycleAdapter.addItem(new Chatting_Item(me_nick_name, "http://seohee", editText.getText().toString(), send_time, Chatting.RIGHT_CONTENT, chat_mode));

                //????????? ????????????!
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

                editText.setText("");

                chatting_message_count++;
                linearLayoutManager.scrollToPosition(chatting_send_recycleAdapter.getItemCount()-1);


            }
        });

    }

    //???????????? ?????????
    public void click_back(View view){
          switch (view.getId()){
            //????????? ?????????
              case R.id.chatting_close:
                //??????????????? ???????????? ????????????,
                  finish();
                  chatting_message_count= 0;
              break;

          }
    }



    //?????? ?????? ??????
    public void FirebaseConnector(){

        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();

        switch (chat_mode){
            case Chatting.PERSONAL:
                databaseReference = firebaseDatabase.getReference(Chatting.PERSONAL);
                break;

            case Chatting.STORE_MANAGER:
                databaseReference = firebaseDatabase.getReference(Chatting.STORE_MANAGER);
                break;
        }
        Read_All_Data();
    }

    //?????? ?????? ????????????, ?????? ????????? ?????? id??? ????????????
    private Map<String, Object> map;
    private Map<String, Object> id_map;
    private Map<String, Object> id_inside_map;

    private HashMap<String, String> input_map;
    private Object[] chatting_map;

    private Map<String, Object> chatrooms_map;
    //?????? id??? ????????? + ?????? ?????? ????????????!
    ArrayList<HashMap<String, String>> chatrooms_arraylist, chatting_arraylist;
    ArrayList<HashMap<String, String>> chatting_me_arraylist = new ArrayList<>(), chatting_other_arraylist = new ArrayList<>();

    private int name, profileUrl, message, time,date;
    private  int me_message_count = 1 , other_message_count = 1;

    private String[] send_chatting_me_date;
    private  String send_chatting_me_time;


    private Object[] send_chatting_me, send_chatting_other;
    private String current_chatting_date ="2021???";





    //?????? ????????????
    public void Read_All_Data(){

        me_message_count = other_message_count =1;
        current_chatting_date ="2021???";

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                map = (Map<String, Object>) dataSnapshot.getValue();

                if(map != null){
                    //????????? ?????? ?????? ??????
                    for(String id: map.keySet()){
                        id_map = (Map<String, Object>) map.get(id);
                    }
                    Log.d(TAG, "?????????????????? : "+ id_map.keySet());


                    for (String id_inside : id_map.keySet()) {
                        //??? ????????? ??????
                        if (id_inside.equals(userId)) {
                            id_inside_map = (Map<String, Object>) id_map.get(id_inside);
                            break;
                        }
                    }
                        Log.d(TAG, "????????? ?????? : "+ id_inside_map);

                        for(String in_inside_key: id_inside_map.keySet()){
                            switch (in_inside_key){
                                //????????? -> ????????? ???????????? ??????
                                case "chatrooms":
                                    chatrooms_arraylist = (ArrayList<HashMap<String, String>>)id_inside_map.get(in_inside_key);
                                    break;

                                //????????? -> ?????? ?????? ???????????? ??????
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

    //????????? ?????? ??????
    public int Find_Chatting_Number(String chat_mode){

        FirebaseDatabase.getInstance().getReference(chat_mode).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                map = (Map<String, Object>) dataSnapshot.getValue();

                if (map != null) {
                    //????????? ?????? ?????? ??????
                    for (String id : map.keySet()) {
                        id_map = (Map<String, Object>) map.get(id);
                    }

                    int check_id_count = 0;
                    for(String id_inside : id_map.keySet()){

                        if(id_inside.equals(userId)){
                            id_inside_map = (Map<String, Object>)id_map.get(id_inside);
                            break;
                        }
                        check_id_count++;
                    }

                    if(check_id_count == id_map.size()){
                        Initialize_Id(userId, chat_mode);
                    }
                    else {
                        for (String in_inside_key : id_inside_map.keySet()) {
                            switch (in_inside_key) {
                                //????????? -> ????????? ???????????? ??????
                                case "chatrooms":
                                    chatrooms_arraylist = (ArrayList<HashMap<String, String>>) id_inside_map.get(in_inside_key);
                                    break;

                                //????????? -> ?????? ?????? ???????????? ??????
                                case "chatting":
                                    chatting_arraylist = (ArrayList<HashMap<String, String>>) id_inside_map.get(in_inside_key);
                                    break;
                            }
                        }

                        int count_break = 0;
                        for(int index = 1 ; index< chatrooms_arraylist.size() ; index++){

                            input_map  = chatrooms_arraylist.get(index);
                            chatting_map = input_map.values().toArray();

                            for(int information_index = 0 ; information_index < chatting_map.length ; information_index++){

                                if(click_chatting_list_name.equals(chatting_map[information_index])){
                                    chatting_number = index;
                                    count_break =1;
                                    break;
                                }
                            }
                        }
                        if(count_break == 0){
                            chatting_number = chatrooms_arraylist.size();

                        }

                    }
                }


            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) { }
        });
        return chatting_number ;
    }

    //?????? ?????? ????????? ??????
    private void Initialize_Id(String insertId, String mode){

        Log.d("?????? ??????", insertId + "/" + mode);
        FirebaseDatabase.getInstance().getReference(mode).child("id").child(insertId).child("chatrooms").child("0").setValue("???????????????");
        FirebaseDatabase.getInstance().getReference(mode).child("id").child(insertId).child("chatting").child("0").setValue("???????????????");

    }

    //???????????? ????????????
    private void getChatting() {
        if( chatting_arraylist.size() != chatting_number) {
            input_map = chatting_arraylist.get(chatting_number);
            chatting_map = input_map.values().toArray();
            chatrooms_map = (Map<String, Object>) chatting_map[0];

            chatting_me_arraylist = (ArrayList<HashMap<String, String>>) chatrooms_map.get("me");
            chatting_other_arraylist = (ArrayList<HashMap<String, String>>) chatrooms_map.get("other");

            chatting_message_count = chatting_me_arraylist.size();
            Log.d(TAG, "?????? ?????? ?????? : " + chatting_me_arraylist);
//            Log.d(TAG, "?????? ?????? ?????? ?????? : " + chatting_me_arraylist.get(1));
//            Log.d(TAG, "?????? ?????? ?????? ?????? : " + chatting_me_arraylist.get(1).values());

            //??? ????????? ??????
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


            while (true) {
                if (me_message_count == chatting_me_arraylist.size() ||
                        other_message_count == chatting_other_arraylist.size())
                    break;

                send_chatting_me = chatting_me_arraylist.get(me_message_count).values().toArray();
                send_chatting_other = chatting_other_arraylist.get(other_message_count).values().toArray();

                Insert_Date_Order_Item(send_chatting_me, send_chatting_other);

            }

            while (me_message_count != chatting_me_arraylist.size()) {
                //????????????,,,,?

                send_chatting_me = chatting_me_arraylist.get(me_message_count).values().toArray();
                Check_New_Date(send_chatting_me[date].toString());
                chatting_send_recycleAdapter.addItem(new Chatting_Item(me_nick_name, send_chatting_me[profileUrl].toString(), send_chatting_me[message].toString(), send_chatting_me[time].toString(), Chatting.RIGHT_CONTENT,chat_mode));
                me_message_count++;

                if (me_message_count == chatting_me_arraylist.size())
                    break;

                send_chatting_me = chatting_me_arraylist.get(me_message_count).values().toArray();

            }

            while (other_message_count != chatting_other_arraylist.size()) {
                send_chatting_other = chatting_other_arraylist.get(other_message_count).values().toArray();

                Check_New_Date(send_chatting_other[date].toString());
                chatting_send_recycleAdapter.addItem(new Chatting_Item(other_nick_name, send_chatting_other[profileUrl].toString(), send_chatting_other[message].toString(), send_chatting_other[time].toString(), Chatting.LEFT_CONTENT,chat_mode));
                other_message_count++;

                if (other_message_count == chatting_other_arraylist.size())
                    break;

                send_chatting_other = chatting_other_arraylist.get(other_message_count).values().toArray();

            }
        }
    }



    //??????????????? ????????? ??????
    private void Insert_Date_Order_Item(Object[] send_chatting_me, Object[] send_chatting_other){
        send_chatting_me_date = send_chatting_me[date].toString().split(" ");
        String[] send_chatting_other_date = send_chatting_other[date].toString().split(" ");

        send_chatting_me_time = send_chatting_me[time].toString();
        String send_chatting_other_time = send_chatting_other[time].toString();

        int insert_type = 0;

        insert_type = Compare_Date(send_chatting_me_date, send_chatting_other_date, send_chatting_me_time, send_chatting_other_time);

        Log.d("compare", insert_type + "/" + send_chatting_me_date[2] + "/" + send_chatting_other_date[2]
        +": " + send_chatting_me_time + "/" + send_chatting_other_time );

        switch (insert_type){
            case Chatting.ME:
                Check_New_Date(send_chatting_me[date].toString());
                Log.d(TAG, "???????????? ?????? ????????? 1 -> " + send_chatting_me[message].toString());

                chatting_send_recycleAdapter.addItem(new Chatting_Item(me_nick_name,send_chatting_me[profileUrl].toString(), send_chatting_me[message].toString(),send_chatting_me[time].toString(), Chatting.RIGHT_CONTENT,chat_mode));
                me_message_count++;
                break;

            case Chatting.OTHER:
                Check_New_Date(send_chatting_other[date].toString());
                chatting_send_recycleAdapter.addItem(new Chatting_Item(other_nick_name, send_chatting_other[profileUrl].toString(), send_chatting_other[message].toString(), send_chatting_other[time].toString(), Chatting.LEFT_CONTENT,chat_mode));
                other_message_count++;
                break;
        }



    }

    //?????? ????????????
    public int Compare_Date(String[] send_chatting_me_date, String[] send_chatting_other_date, String send_chatting_me_time, String send_chatting_other_time){
        //???, ???, ??? ???????????? -> ??? ????????? ??????????????????
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
                else if(send_chatting_me_date[2].compareTo(send_chatting_other_date[2]) > 0)
                    return Chatting.OTHER;

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

    //Personal , StoreManger
    //????????? ?????? ???????????? ?????? ????????? ?????? ?????? ??????????????????
    //?????? ?????? ???????????? ????????????????????? !!!!!
    //????????? ?????? ????????? ????????? -> ???????????? ????????? + ????????? ?????? + ????????? ??????????????? ?????????????????????.

    //??? -> userId, ????????? -> click_chatting_list_name

    public int Get_Other_Communication_Chatting() {
        switch (chat_mode) {
            //????????? ?????? ??????
            case Chatting.PERSONAL:
                databaseReference = FirebaseDatabase.getInstance().getReference(Chatting.STORE_MANAGER);
                break;

            case Chatting.STORE_MANAGER:
                databaseReference = FirebaseDatabase.getInstance().getReference(Chatting.PERSONAL);
                break;
        }

        //????????? ?????? ????????? ?????? click_chatting_list_name
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                map = (Map<String, Object>) dataSnapshot.getValue();

                if (map != null) {
                    //????????? ?????? ?????? ??????
                    for (String id : map.keySet()) {
                        id_map = (Map<String, Object>) map.get(id);
                    }

                    Log.d("????????? ??????", id_map.keySet().toString());

                    int check_id_count = 0;
                    for (String id_inside : id_map.keySet()) {
                        Log.d("???", id_inside + "/" + id_map.size());
                        //????????? ????????? ??????
                        if (id_inside.equals(click_chatting_list_name)) {
                            id_inside_map = (Map<String, Object>) id_map.get(id_inside);
                            break;
                        }
                        check_id_count++;
                    }

                    Log.d("?????? ", check_id_count + "/" + id_map.toString());

                    //???????????? ???????????? ????????? ?????? ???????????? ?????????? ????????????!
                    if (check_id_count == id_map.size()) {
                        if (chat_mode.equals(Chatting.STORE_MANAGER))
                            Initialize_Id(click_chatting_list_name, Chatting.PERSONAL);
                        else
                            Initialize_Id(click_chatting_list_name, Chatting.STORE_MANAGER);
                    }
                    //????????? ???????????? ???????????? ???????
                    else{
                        for (String id_inside : id_map.keySet()) {

                            //????????? ????????? ??????
                            if (id_inside.equals(click_chatting_list_name)) {
                                id_inside_map = (Map<String, Object>) id_map.get(id_inside);
                                break;
                            }
                        }
                        Log.d("???????????? ????????????", id_inside_map.toString());

                        for(String in_inside_key: id_inside_map.keySet()){
                            switch (in_inside_key){
                               case "chatting":
                                    chatting_arraylist = (ArrayList<HashMap<String, String>>)id_inside_map.get(in_inside_key);
                                    break;
                            }
                        }
                        Log.d("???????????? ?????????????????????", chatting_arraylist.toString());

                        if(chatting_arraylist.size() >1){

                            for(int index = 1; index< chatting_arraylist.size(); index++){
                                input_map = chatting_arraylist.get(index);
                                chatting_map = input_map.values().toArray();

                                Log.d("?????????", chatting_map[0].toString());
                                chatrooms_map =  (Map<String, Object>)chatting_map[0];

                                Log.d(TAG, "?????? ?????? ??????   : " + chatrooms_map.keySet());

                                chatting_me_arraylist = (ArrayList<HashMap<String, String>>) chatrooms_map.get("other");

                                Log.d("?????? ?????? ??????", chatrooms_map.toString());
                                if(chatting_me_arraylist.get(0).values().toArray()[2].equals(userId)){
                                    other_chatting_number = index;
                                    break;
                                }
                            }

                        }
                    }



                }
            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {
            }
        });

        return other_chatting_number;
    }






    //???????????? ???????????????
    private void get_personal_NickName(String userId) {
        ServiceApi serviceApi=  new RetrofitBulider().initRetrofit();
        Call<Personal> call = serviceApi.getPersonalData(userId);
        new Insert_Personal_NickName().execute(call);
    }

    private class Insert_Personal_NickName extends AsyncTask<Call, Void, String>{
        @Override
        protected String doInBackground(Call... calls) {
            try{
                Call<Personal> call = calls[0];
                Personal personalData = call.execute().body();
                return  personalData.getNickName();

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String nickName){
            if(chat_mode.equals(Chatting.STORE_MANAGER)){
                other_nick_name = nickName;
                chatting_other_name = findViewById(R.id.other_userName);
                chatting_other_name.setText(other_nick_name);
            }
            else{
                me_nick_name = nickName;
            }
        }
    }

    //?????? ?????????
    private void get_storeManager_NickName(String userId) {
        ServiceApi serviceApi=  new RetrofitBulider().initRetrofit();
        Call<Merchant> call = serviceApi.getStoreName(userId);
        new Insert_storeManager_NickName().execute(call);
    }

    private class Insert_storeManager_NickName extends AsyncTask<Call, Void, String>{
        @Override
        protected String doInBackground(Call... calls) {
            try{
                Call<Merchant> call = calls[0];
                Merchant merchantData = call.execute().body();
                return  merchantData.getStoreName();

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String nickName){
            if(chat_mode.equals(Chatting.STORE_MANAGER)){
                me_nick_name = nickName;
            }
            else{
                other_nick_name = nickName;
                chatting_other_name = findViewById(R.id.other_userName);
                chatting_other_name.setText(other_nick_name);
            }
        }
    }
}

