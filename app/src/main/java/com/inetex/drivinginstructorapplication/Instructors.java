package com.inetex.drivinginstructorapplication;


public class Instructors {
    String name;
    String city;
    int avatar;
    String age;
    String experience;
    String rating;
    String typeVehicle;
    int pricePerHours;
    String url;
    String workingDays;
    String workingHours;

    public Instructors(String name, String city, int avatar, String age, String experience, String rating, String typeVehicle, int pricePerHours, String url, String workingDays, String workingHours) {
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
    }

    public Instructors() {
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public int getAvatar() {
        return avatar;
    }

    public String getAge() {
        return age;
    }

    public String getExperience() {
        return experience;
    }

    public String getRating() {
        return rating;
    }

    public String getTypeVehicle() {
        return typeVehicle;
    }

    public int getPricePerHours() {
        return pricePerHours;
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

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setTypeVehicle(String typeVehicle) {
        this.typeVehicle = typeVehicle;
    }

    public void setPricePerHours(int pricePerHours) {
        this.pricePerHours = pricePerHours;
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

    @Override
    public String toString() {
        return "Instructors{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", avatar=" + avatar +
                ", age='" + age + '\'' +
                ", experience='" + experience + '\'' +
                ", rating='" + rating + '\'' +
                ", typeVehicle='" + typeVehicle + '\'' +
                ", pricePerHours=" + pricePerHours +
                ", url='" + url + '\'' +
                ", workingDays='" + workingDays + '\'' +
                ", workingHours='" + workingHours + '\'' +
                '}';
    }
}

