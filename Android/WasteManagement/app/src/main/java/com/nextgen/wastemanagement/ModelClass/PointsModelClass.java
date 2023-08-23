package com.nextgen.wastemanagement.ModelClass;

public class PointsModelClass {

    String id;
    String points;
    String desc;

    public PointsModelClass(String id,String points,String desc) {
        this.id = id;
        this.points = points;
        this.desc = desc;
    }

    public String getId(){
        return id;
    }

    public String getPoints(){
        return points;
    }

    public String getDesc(){
        return desc;
    }
}
