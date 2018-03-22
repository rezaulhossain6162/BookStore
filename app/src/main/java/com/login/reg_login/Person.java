package com.login.reg_login;

/**
 * Created by jack on 9/25/2017.
 */

public class Person {
    String name,bookName,uri,uid;

    public Person(String name, String bookName, String uri, String uid) {
        this.name = name;
        this.bookName = bookName;
        this.uri = uri;
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

    public String getUri() {
        return uri;
    }

    public void setKey(String uri) {
        this.uri = uri;
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
