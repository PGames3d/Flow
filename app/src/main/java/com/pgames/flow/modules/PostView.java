package com.pgames.flow.modules;

public class PostView {
    private String Company;
    private String Desc;
    private String Education;
    private String Location;
    private String Name;
    private String Phone;
    private String Skill;
    private String Title;
    private String IsEducation;

    public PostView() {
    }

    public PostView(String company, String desc, String education, String location, String name, String phone, String skill, String title, String isEducation) {
        Company = company;
        Desc = desc;
        Education = education;
        Location = location;
        Name = name;
        Phone = phone;
        Skill = skill;
        Title = title;
        IsEducation = isEducation;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getEducation() {
        return Education;
    }

    public void setEducation(String education) {
        Education = education;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
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

    public String getSkill() {
        return Skill;
    }

    public void setSkill(String skill) {
        Skill = skill;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getIsEducation() {
        return IsEducation;
    }

    public void setIsEduaction(String isEducation) {
        IsEducation = isEducation;
    }
}
