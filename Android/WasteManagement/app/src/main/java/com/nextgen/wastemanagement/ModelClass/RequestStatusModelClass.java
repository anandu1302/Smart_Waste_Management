package com.nextgen.wastemanagement.ModelClass;

public class RequestStatusModelClass {

    String id;
    String name;
    String date;
    String time;
    String requested_date;
    String status;

    public RequestStatusModelClass(String id,String name,String date,String time,String requested_date,String status) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.requested_date = requested_date;
        this.status = status;
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

    public String getStatus(){
        return status;
    }
}
