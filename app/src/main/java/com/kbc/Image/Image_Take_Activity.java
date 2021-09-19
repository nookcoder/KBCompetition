package com.kbc.Image;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.kbc.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Image_Take_Activity extends AppCompatActivity implements
        View.OnClickListener{

    private ImageView get_image;
    private Button open_carmera, open_gallery;
    private Uri image_Uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_take_activity);

        get_image = findViewById(R.id.get_image);
        open_carmera = findViewById(R.id.open_carmera);
        open_gallery = findViewById(R.id.open_gallery);

        open_carmera.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            //카메라 촬영
            case R.id.open_carmera:
                //카메라 기능 열기!
                Intent carmera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //사진 파일 변수 선언 + 경로 설정
                File image_file = null;
                try {
                    {
                        image_file = createImageFile();
                    }
                }catch (IOException exception){
                }
                //사진 저장하고 이미지뷰에 띄우기
                if(image_file != null){
                    image_Uri = FileProvider.getUriForFile(this,getPackageName()+
                            ".fileprovider", image_file);
                    carmera_intent.putExtra(MediaStore.EXTRA_OUTPUT, image_Uri);
                    startActivityForResult(carmera_intent, 0);

                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        //카메라 촬영 -> 이미지뷰에 띄워주세요~!
        if(requestCode == 0 && requestCode == RESULT_OK){
            //이미지뷰에 파일 경로 사진을 가져와서 출력!
            get_image.setImageURI(image_Uri);
        }
    }
    //image_file 경로 가져오기
    private File createImageFile() throws  IOException{
        //파일 경로 세팅 + 저장경로
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File StorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File get_image_file = File.createTempFile(imageFileName, ".jpg", storageDir);

        return get_image_file;
    }
}