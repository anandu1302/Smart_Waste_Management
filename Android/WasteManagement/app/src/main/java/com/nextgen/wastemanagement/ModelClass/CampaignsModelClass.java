package com.nextgen.wastemanagement.ModelClass;

public class CampaignsModelClass {

    String id;
    String title;
    String image;

    public CampaignsModelClass(String id,String title,String image) {
        this.id = id;
        this.title = title;
        this.image = image;
    }

    public String getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getImage(){
        return image;
    }

}
