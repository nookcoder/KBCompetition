package com.kbc.Image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.IOException;
import java.io.Serializable;

public class Image_Item implements Serializable {

    private String image_type;
    private String image_file_path;

    public Image_Item(){}
    public Image_Item(String image_type, String image_file_path) {
        this.image_type = image_type;
        this.image_file_path = image_file_path;
    }

    public String getImage_type() {
        return image_type;
    }

    public void setImage_type(String image_type) {
        this.image_type = image_type;
    }

    public String getImage_file_path() {
        return image_file_path;
    }

    public void setImage_file_path(String image_file_path) {
        this.image_file_path = image_file_path;
    }

    public Bitmap get_Bitmap() {

        //카메라로 촬영!!!!
        if (image_type.equals(Image.CARMERA_TYPE)) {
            //이미지뷰에 파일 경로 사진을 가져와서 출력!
            Bitmap bitmap = BitmapFactory.decodeFile(image_file_path);
            ExifInterface exifInterface = null;

            try {
                exifInterface = new ExifInterface(image_file_path);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            //이미지 회전 각도
            int orientation, degree;

            if (exifInterface != null) {
                orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL);
                degree = Calculate_Image_Orientation(orientation);
            } else {
                degree = 0;
            }
            return  Rotate_Image(bitmap, degree);
        }
        // 갤러리에서 가져온 이미지
        else if (image_type.equals(Image.GALLERY_TYPE)) {

            BitmapFactory.Options options = new BitmapFactory.Options();
            return BitmapFactory.decodeFile(image_file_path, options);
        }
        return null;
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

}
