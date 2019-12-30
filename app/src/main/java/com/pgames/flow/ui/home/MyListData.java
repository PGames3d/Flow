package com.pgames.flow.ui.home;

import java.io.Serializable;

class MyListData implements Serializable {

    private String name,
            phone,
            address,
            profilePic,
            work;

    MyListData(String name, String phone, String address, String profilePic, String work) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.profilePic = profilePic;
        this.work = work;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }
}