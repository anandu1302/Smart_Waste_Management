package com.nextgen.wastemanagement.ModelClass;

public class WasteReportModelClass {

    String id;
    String image;
    String desc;
    String date;
    String latitude;
    String longitude;
    String status;

    public WasteReportModelClass(String id,String image,String desc,String date,String latitude,String longitude,String status) {
        this.id = id;
        this.image = image;
        this.desc = desc;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
    }

    public String getId(){
        return id;
    }

    public String getImage(){
        return image;
    }

    public String getDesc(){
        return desc;
    }

    public String getDate(){
        return date;
    }

    public String getLatitude(){
        return latitude;
    }

    public String getLongitude(){
        return longitude;
    }

    public String getStatus(){
        return status;
    }
}
