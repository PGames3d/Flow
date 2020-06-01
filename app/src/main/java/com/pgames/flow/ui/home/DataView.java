package com.pgames.flow.ui.home;

public class DataView {
    private String Goan;
    private String Name;
    private String Phone;
    private String Requirement;
    private String Taluka;

    public DataView() {

    }

    public DataView(String goan, String name, String phone, String requirement, String taluka) {
        Goan = goan;
        Name = name;
        Phone = phone;
        Requirement = requirement;
        Taluka = taluka;
    }

    public String getGoan() {
        return Goan;
    }

    public void setGoan(String goan) {
        Goan = goan;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getRequirement() {
        return Requirement;
    }

    public void setRequirement(String requirement) {
        Requirement = requirement;
    }

    public String getTaluka() {
        return Taluka;
    }

    public void setTaluka(String taluka) {
        Taluka = taluka;
    }
}
