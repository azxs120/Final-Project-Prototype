package com.example.prototype.DecisionTrees;


import com.example.prototype.DBClasess.Apartment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class DecisionTree {
    ArrayList<Apartment> apartments = new ArrayList<>();

    public DecisionTree(ArrayList<Apartment> apartments) {
        this.apartments = apartments;
        makeTree();
    }

    private Tree makeTree() {
        return null;
    }




}
