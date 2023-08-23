package com.nextgen.wastemanagement.ModelClass;

public class RequestModelClass {

    String id;
    String name;
    String date;
    String time;
    String requested_date;
    String location;

    public RequestModelClass(String id,String name,String date,String time,String requested_date,String location) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.requested_date = requested_date;
        this.location = location;
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

    public String getLocation(){
        return location;
    }
}
