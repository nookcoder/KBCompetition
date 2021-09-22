package com.kbc.Payment_Personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.kbc.Personal_MainActivity;
import com.kbc.Pickup.Personal_PickupDetailActivity;
import com.kbc.R;

public class Payment_Finished extends AppCompatActivity {
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_finished);

        next=(Button)findViewById(R.id.finished_payment);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(Payment_Finished.this,Personal_MainActivity.class);
                startActivity(back);
            }
        });
    }
}
