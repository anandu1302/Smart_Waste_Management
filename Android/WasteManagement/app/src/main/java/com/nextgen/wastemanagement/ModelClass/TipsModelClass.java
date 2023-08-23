package com.nextgen.wastemanagement.ModelClass;

public class TipsModelClass {

    String id;
    String title;
    String desc;

    public TipsModelClass(String id,String title,String desc) {
        this.id = id;
        this.title = title;
        this.desc = desc;
    }

    public String getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getDesc(){
        return desc;
    }
}
