package com.nextgen.wastemanagement.ModelClass;

public class BinModelClass {

    String id;
    String name;
    String description;
    String image;

    public BinModelClass(String id,String name,String description,String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public String getImage(){
        return image;
    }
}
