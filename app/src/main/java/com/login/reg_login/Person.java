package com.login.reg_login;

/**
 * Created by jack on 9/25/2017.
 */

public class Person {
    String name,address,phone,key,uid;
    public String getUid(){
     return uid;
}
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
    public String getKey(){
        return key;
    }

    public Person(String name, String address, String phone,String key,String uid) {
        this.uid=uid;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.key=key;
    }
    public Person(){

    }
}
