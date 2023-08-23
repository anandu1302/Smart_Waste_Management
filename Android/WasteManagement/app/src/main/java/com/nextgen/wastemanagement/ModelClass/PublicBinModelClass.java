package com.nextgen.wastemanagement.ModelClass;

public class PublicBinModelClass {

    String id;
    String location;
    String latitude;
    String longitude;

    public PublicBinModelClass(String id,String location,String latitude,String longitude) {
        this.id = id;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId(){
        return id;
    }

    public String getLocation(){
        return location;
    }

    public String getLatitude(){
        return latitude;
    }

    public String getLongitude(){
        return longitude;
    }
}
