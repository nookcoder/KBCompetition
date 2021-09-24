package com.kbc.Common;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Creating {
    // pickUpDate 출력 함수
    public String pickUpDate(String year,String month,String day){
        return year+" "+month+" "+day;
    }

    // pickUpTime 출력함수
    public String pickUpTime(String noon,String hour,String minute){
        return noon + " "+hour+" "+minute+" ";
    }

    public RequestBody requestBody(String text){
        return  RequestBody.create(MediaType.parse("text/plain"),text);
    }
}
