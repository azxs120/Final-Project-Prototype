package com.example.prototype.DBClasess;

import java.io.Serializable;

public class Review implements Serializable {

    private String bodyReview;
    private String dateReview;

    public Review(String bodyReview,String dateReview){
        this.bodyReview = bodyReview;
        this.dateReview = dateReview;
    }

    public String getReview() {
        return bodyReview;
    }

    public String getDate() {
        return dateReview;
    }
}
