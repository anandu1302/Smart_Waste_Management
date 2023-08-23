package com.nextgen.wastemanagement.ModelClass;

public class MessageModelClass {

    String id;
    String uid;
    String message;

    public MessageModelClass(String id,String uid,String message) {
        this.id = id;
        this.uid = uid;
        this.message = message;
    }

    public String getId(){
        return id;
    }

    public String getUid(){
        return uid;
    }

    public String getMessage(){
        return message;
    }
}
