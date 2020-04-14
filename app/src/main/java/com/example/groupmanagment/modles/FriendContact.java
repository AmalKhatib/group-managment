package com.example.groupmanagment.modles;

/*this class has the types and name of the attributes of the "friends" table on the DB
* it is used as an object to assign each row data into it, and then it is added into the arraylist of the rows*/

import java.io.Serializable;

public class FriendContact implements Serializable {

    private String name, email;
    private String mobileNumber;

    public FriendContact(String name, String email, String mobileNumber) {
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
