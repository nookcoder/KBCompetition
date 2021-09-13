package com.kbc;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

//사업자 정보 등록
public class StoreManager_Add_Manager_Info_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storemanager_add_manager_info);

        //다음 버튼
        Button next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent로 화면 전환 + [user : 사업자] 전달
                Intent intent = new Intent(getApplicationContext(),Added_Done_Activity.class);
                intent.putExtra("user","store manager");
                startActivity(intent);
            }
        });

    }
}
