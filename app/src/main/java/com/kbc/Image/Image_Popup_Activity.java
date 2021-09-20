package com.kbc.Image;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.kbc.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class Image_Popup_Activity extends AppCompatActivity {

    private ImageButton image_register_close;
    private Button open_carmera, open_gallery;

    private Uri image_Uri;
    private File image_file;
    private String image_type ;
    private String image_file_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.image_popup_activity);

        //컴포넌트 가져오기
        image_register_close = findViewById(R.id.image_register_close);
        open_carmera = findViewById(R.id.open_carmera);
        open_gallery = findViewById(R.id.open_gallery);

        Get_Carmera_Permission();
    }

    public void close_image_register(View view){
        finish();
    }
    public void open_carmera(View view){
        //카메라 기능 열기!
        Intent carmera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //사진 파일 변수 선언 + 경로 설정
        image_file = null;
        try {
            {
                image_file = Create_Image_File();
            }
        }catch (IOException exception){
        }
        //사진 저장!!!
        if(image_file != null){
            image_Uri = FileProvider.getUriForFile(this,getPackageName()+
                    ".fileprovider", image_file);
            carmera_intent.putExtra(MediaStore.EXTRA_OUTPUT, image_Uri);
            startActivityForResult(carmera_intent, Image.PICK_FROM_CAMERA);
        }
    }

    public void open_gallery(View view){
        Intent gallery_intent = new Intent(Intent.ACTION_PICK);
        gallery_intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(gallery_intent, Image.PICK_FROM_GALLERY);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        //카메라 촬영 -> 이미지뷰에 띄워주세요~!
        if (requestCode == Image.PICK_FROM_CAMERA && resultCode == RESULT_OK) {
            image_type = Image.CARMERA_TYPE;

        }
        //갤러리에서 가져오기
        else if (requestCode == Image.PICK_FROM_GALLERY && resultCode == RESULT_OK) {
            image_Uri = data.getData();
            Log.d("image_Uri", image_Uri + "");
            Cursor cursor = null;

            try {
                String[] proj = {MediaStore.Images.Media.DATA};

                assert image_Uri != null;
                cursor = getContentResolver().query(image_Uri, proj, null, null, null);

                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                image_file = new File(cursor.getString(column_index));

                image_file_path = image_file.getAbsolutePath();

            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            image_type = Image.GALLERY_TYPE;
        }
        //사진 체크 해주세여,,,,ㅠㅠㅠ
        Intent image_check_intent = new Intent(Image_Popup_Activity.this, Image_Check_Activity.class);
        image_check_intent.putExtra("image_type", image_type);
        image_check_intent.putExtra("image_file_path", image_file_path);
        startActivity(image_check_intent);
        finish();
    }


        //이미지 권한 요청
    private void Get_Carmera_Permission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED){
                Log.d("카메라 권한", "권한 설정 완료");
            }
            else {
                Log.d("카메라 권한", "권한 설정 요청");
                ActivityCompat.requestPermissions(Image_Popup_Activity.this,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
        }
    }

    //image_file 경로 가져오기
    private File Create_Image_File() throws  IOException{
        //파일 경로 세팅 + 저장경로
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File StorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File get_image_file = File.createTempFile(imageFileName, ".jpg", storageDir);

        image_file_path = get_image_file.getAbsolutePath();

        return get_image_file;
    }
    //실질적인 권한 요청
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    }

    //바깥레이어 클릭시 안닫히게
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    //안드로이드 백버튼 막기
    @Override
    public void onBackPressed(){
        return;
    }
}