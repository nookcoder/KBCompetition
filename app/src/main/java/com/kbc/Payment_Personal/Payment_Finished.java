package com.kbc.Payment_Personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.kbc.Personal_MainActivity;
import com.kbc.Pickup.Personal_PickupDetailActivity;
import com.kbc.R;
import com.kbc.Sale.Sale_Item;

import java.util.ArrayList;

public class Payment_Finished extends AppCompatActivity {
    Button next;
    String personal_id,personal_town2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_finished);
        Intent intent = getIntent();
        personal_id = intent.getExtras().getString("userID",personal_id);
        personal_town2 = intent.getExtras().getString("town2",personal_town2);


        next=(Button)findViewById(R.id.finished_payment);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(Payment_Finished.this,Personal_MainActivity.class);
                back.putExtra("userID",personal_id);
                back.putExtra("town2",personal_town2);
                startActivity(back);
            }
        });
    }
}
