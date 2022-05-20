package com.example.prototype;

import android.provider.ContactsContract;

public class NewUser {
    public String firstName,secName,email,password,identity;


    public NewUser() {
    }

    public NewUser(String firstName, String secName, String email, String password,String identity){
        this.firstName = firstName;
        this.secName = secName;
        this.email = email;
        this.password = password;
        this.identity = identity;
    }

    //public String getIdentity(){  return identity;}

    public void setIdentity(String identity){this.identity=identity;}

}

