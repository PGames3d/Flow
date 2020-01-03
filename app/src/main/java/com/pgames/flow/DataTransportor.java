package com.pgames.flow;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DataTransportor {
  public static Map<String,Object> mDataMap = new HashMap<>();
  public static Map<String,Object> mArrangeDataMap = new HashMap<>();
  public DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();



  public Map<String, Object> getmUserMap() {
        return mDataMap;
    }

    public void setmUserMap(Map<String, Object> dataMap) {
        DataTransportor.mDataMap = dataMap;
    }

    public static Map<String, Object> getmArrangeDataMap() {
        return mArrangeDataMap;
    }

    public static void setmArrangeDataMap(Map<String, Object> mArrangeDataMap) {
        DataTransportor.mArrangeDataMap = mArrangeDataMap;
    }

    private String
            Name = "",
            District = "",
            Taluka = "",
            Goan = "",
            Pin = "",
            Address = "",
            Type = "",
            work = "",
            profilepic = "";

    public DataTransportor(String name, String district, String taluka, String goan, String pin, String address, String type, String work, String profilepic) {
        Name = name;
        District = district;
        Taluka = taluka;
        Goan = goan;
        Pin = pin;
        Address = address;
        Type = type;
        this.work = work;
        this.profilepic = profilepic;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getTaluka() {
        return Taluka;
    }

    public void setTaluka(String taluka) {
        Taluka = taluka;
    }

    public String getGoan() {
        return Goan;
    }

    public void setGoan(String goan) {
        Goan = goan;
    }

    public String getPin() {
        return Pin;
    }

    public void setPin(String pin) {
        Pin = pin;
    }

    public String getAddress() {
        return Goan+","+Taluka+","+Pin;
    }

    public void setAddress(String goan,String taluka,String pin) {
        Address = goan+","+taluka+","+pin;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }



}
