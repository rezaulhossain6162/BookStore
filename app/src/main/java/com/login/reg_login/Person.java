package com.login.reg_login;

/**
 * Created by jack on 9/25/2017.
 */

public class Person {
    String name,bookName,key,uid;

    public Person(String name, String bookName, String key, String uid) {
        this.name = name;
        this.bookName = bookName;
        this.key = key;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
          this.bookName=bookName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Person(){

    }
}
