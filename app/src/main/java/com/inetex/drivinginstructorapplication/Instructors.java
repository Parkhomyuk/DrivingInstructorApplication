package com.inetex.drivinginstructorapplication;


public class Instructors {
    String name;
    String city;
    int avatar;
    String age;
    String experience;
    String rating;
    String typeVehicle;

    public Instructors(String name, String city, int avatar, String age, String experience, String rating, String typeVehicle) {
        this.name = name;
        this.city = city;
        this.avatar = avatar;
        this.age=age;
        this.experience = experience;
        this.rating = rating;
        this.typeVehicle=typeVehicle;
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
                '}';
    }
}
