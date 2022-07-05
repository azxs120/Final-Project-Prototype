package com.example.prototype.DBConnections;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseConnection {
    private static FirebaseAuth mAuth = null;
    private static FirebaseFirestore db = null;

    /**
     * Initialize Firebase Auth
     * @return an instance of Auth
     */
    public static FirebaseAuth getFirebaseAuth(){
        if(mAuth == null)
            return FirebaseAuth.getInstance();
        return mAuth;
    }

    /**
     * Initialize Firebase Firestore
     * @return an instance of DB
     */
    public static FirebaseFirestore getFirebaseFirestore(){
        if(db == null)
            return FirebaseFirestore.getInstance();
        return db;
    }
}
