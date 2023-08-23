package com.nextgen.wastemanagement.ModelClass;

public class WasteCollectedModelClass {

    String id;
    String name;
    String date;
    String time;
    String requested_date;
    String wid;
    String amount;

    public WasteCollectedModelClass(String id,String name,String date,String time,String requested_date,String wid,String amount) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.requested_date = requested_date;
        this.wid = wid;
        this.amount = amount;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getDate(){
        return date;
    }

    public String getTime(){
        return time;
    }

    public String getRequested_date(){
        return requested_date;
    }

    public String getWid(){
        return wid;
    }

    public String getAmount(){
        return amount;
    }
}
