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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.kbc.R;
import com.kbc.Sale.StoreManager_Product_Register_Activity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class Image_Take_Activity extends AppCompatActivity implements
        View.OnClickListener{

    private ImageView get_image;
    private Button open_carmera, open_gallery;
    private Uri image_Uri;
    private File image_file;
    String imge_file_path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_take_activity);

        get_image = findViewById(R.id.get_image);
        open_carmera = findViewById(R.id.open_carmera);
        open_gallery = findViewById(R.id.open_gallery);

        Get_Carmera_Permission();
        open_carmera.setOnClickListener(this);
        open_gallery.setOnClickListener(this);



    }

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
                ActivityCompat.requestPermissions(Image_Take_Activity.this,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
        }
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
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            //카메라 촬영
            case R.id.open_carmera:
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
                //사진 저장하고 이미지뷰에 띄우기
                if(image_file != null){
                    image_Uri = FileProvider.getUriForFile(this,getPackageName()+
                            ".fileprovider", image_file);
                    carmera_intent.putExtra(MediaStore.EXTRA_OUTPUT, image_Uri);
                    startActivityForResult(carmera_intent, Image.PICK_FROM_CAMERA);
                }
                break;

            case R.id.open_gallery:
                Intent gallery_intent = new Intent(Intent.ACTION_PICK);
                gallery_intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(gallery_intent, Image.PICK_FROM_GALLERY);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        //카메라 촬영 -> 이미지뷰에 띄워주세요~!
        if(requestCode == Image.PICK_FROM_CAMERA && resultCode == RESULT_OK){
            //이미지뷰에 파일 경로 사진을 가져와서 출력!

            Bitmap bitmap = BitmapFactory.decodeFile(imge_file_path);
            ExifInterface exifInterface = null;

            try{
                exifInterface = new ExifInterface(imge_file_path);
            }catch (IOException ioException){
                ioException.printStackTrace();
            }

            //이미지 회전 각도
            int orientation, degree;

            if(exifInterface != null){
                orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL);
                degree = Calculate_Image_Orientation(orientation);
            } else {
                degree = 0;
            }

            get_image.setImageBitmap(Rotate_Image(bitmap,degree));
            Log.d("이미지 경로", image_Uri.toString());
            Log.d("외부 저장소", Environment.getExternalStorageDirectory().getAbsolutePath());
        }
        //갤러리에서 가져오기
        else if (requestCode == Image.PICK_FROM_GALLERY  && resultCode == RESULT_OK){
            image_Uri = data.getData();
            Log.d("image_Uri" , image_Uri+"");
            Cursor cursor = null;

            try {

                /*
                 *  Uri 스키마를
                 *  content:/// 에서 file:/// 로  변경한다.
                 */
                String[] proj = { MediaStore.Images.Media.DATA };

                assert image_Uri != null;
                cursor = getContentResolver().query(image_Uri, proj, null, null, null);

                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                image_file = new File(cursor.getString(column_index));

                Log.d(TAG, "tempFile Uri : " + Uri.fromFile(image_file));

            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap original_Bitmap = BitmapFactory.decodeFile(image_file.getAbsolutePath(), options);
            get_image.setImageBitmap(original_Bitmap);
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

        imge_file_path = get_image_file.getAbsolutePath();

        return get_image_file;
    }

    //이미지 돌아간 각도 계산
    private int Calculate_Image_Orientation(int orientation){
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90)
            return 90;

        else if (orientation == ExifInterface.ORIENTATION_ROTATE_180)
            return  180;

        else if (orientation == ExifInterface.ORIENTATION_ROTATE_270)
            return 270;

        return  0;
    }

    //이미지 돌리기 !!
    private Bitmap Rotate_Image(Bitmap bitmap, float degree){
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);

        return Bitmap.createBitmap(bitmap,0,0,
                bitmap.getWidth(), bitmap.getHeight(), matrix,true);
    }

    //진짜 이미지 띄우기
    private void Set_Image(){

    }
}