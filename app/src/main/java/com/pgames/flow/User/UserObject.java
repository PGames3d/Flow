package com.pgames.flow.user;

import java.io.Serializable;

public class UserObject implements Serializable {
    String uid,
            name,
            phone;

    public UserObject(String uid){
        this.uid = uid;
    }

    public UserObject(String uid, String name, String phone){
        this.uid = uid;
        this.name = name;
        this.phone = phone;
    }

    public String getUid() {
        return uid;
    }
    public String getPhone() {
        return phone;
    }
    public String getName() {
        return name;
    }
}
