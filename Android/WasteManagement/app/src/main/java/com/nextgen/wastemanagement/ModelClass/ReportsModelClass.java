package com.nextgen.wastemanagement.ModelClass;

public class ReportsModelClass {

    String id;
    String image;
    String desc;
    String date;
    String status;

    public ReportsModelClass(String id,String image,String desc,String date,String status) {
        this.id = id;
        this.image = image;
        this.desc = desc;
        this.date = date;
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

    public String getStatus(){
        return status;
    }
}
