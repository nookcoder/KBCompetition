package com.kbc.Common;

public class Creating {
    // pickUpDate 출력 함수
    public String pickUpDate(String year,String month,String day){
        return year+" "+month+" "+day;
    }

    // pickUpTime 출력함수
    public String pickUpTime(String noon,String hour,String minute){
        return noon + " "+hour+"시"+minute+"분";
    }
}
