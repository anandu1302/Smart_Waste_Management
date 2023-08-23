package com.nextgen.wastemanagement.ModelClass;

public class FeedbacksModelClass {

    String id;
    String name;
    String rating;
    String feedback;

    public FeedbacksModelClass(String id,String name,String rating,String feedback) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.feedback = feedback;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getRating(){
        return rating;
    }

    public String getFeedback(){
        return feedback;
    }
}
