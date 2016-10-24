package com.inetex.drivinginstructorapplication;


public class Instructors {
    String name;
    String city;
    String avatar;
    int age;
    int experience;
    int rating;
    String typeVehicle;
    int pricePerHours;
    String url;
    String workingDays;
    String workingHours;
    String phon;
    String school;
    String email;
    String password;
    String transmission;
    String sex;

    public Instructors(String name, String city, String avatar, int age, int experience, int rating, String typeVehicle, int pricePerHours, String url, String workingDays, String workingHours, String phon, String school, String email, String password,String tranmission,String sex) {
        this.name = name;
        this.city = city;
        this.avatar = avatar;
        this.age = age;
        this.experience = experience;
        this.rating = rating;
        this.typeVehicle = typeVehicle;
        this.pricePerHours = pricePerHours;
        this.url = url;
        this.workingDays = workingDays;
        this.workingHours = workingHours;
        this.phon = phon;
        this.school = school;
        this.email = email;
        this.password = password;
        this.transmission=tranmission;
        this.sex=sex;
    }

    public Instructors() {
    }

    public String getPhon() {
        return phon;
    }

    public String getSchool() {
        return school;
    }

    public void setPhon(String phon) {
        this.phon = phon;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public int getRating() {
        return rating;
    }

    public int getPricePerHours() {
        return pricePerHours;
    }

    public int getExperience() {
        return experience;
    }



    public String getTypeVehicle() {
        return typeVehicle;
    }



    public String getUrl() {
        return url;
    }

    public String getWorkingDays() {
        return workingDays;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public void setTypeVehicle(String typeVehicle) {
        this.typeVehicle = typeVehicle;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setWorkingDays(String workingDays) {
        this.workingDays = workingDays;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setPricePerHours(int pricePerHours) {
        this.pricePerHours = pricePerHours;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}

