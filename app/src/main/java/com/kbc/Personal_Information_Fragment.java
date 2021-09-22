package com.kbc;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kbc.Popup_TwoButton_Activity;
import com.kbc.R;
import com.kbc.StoreManger.StoreManager_MainActivity;

public class Personal_Information_Fragment extends Fragment implements View.OnClickListener{

    private Bundle bundle;
    private String userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.personal_information_fragment, container, false);

        bundle = getArguments();
        if(bundle != null){
            userId = bundle.getString("userID");
        }
        Log.d("내 정보 프래그먼트 아이디 ->", userId);

        Button logout_button = (Button)viewGroup.findViewById(R.id.logout);
        Button withdrawal_button = (Button)viewGroup.findViewById(R.id.withdrawal);
        withdrawal_button.setOnClickListener(this);
        logout_button.setOnClickListener(this);

        return viewGroup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent((Personal_MainActivity)getActivity(),Popup_TwoButton_Activity.class);

        switch (v.getId()){
                //로그아웃
            case R.id.logout:
                intent.putExtra("button_name", "logout");
                startActivity(intent);
                break;

                //탈퇴하기
            case R.id.withdrawal:
                intent.putExtra("button_name", "withdrwal");
                startActivity(intent);
                break;
        }
    }
}
