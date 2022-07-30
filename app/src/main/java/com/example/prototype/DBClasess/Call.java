package com.example.prototype.DBClasess;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

public class Call implements Serializable {
    static int counterForID = 0;

    private String subject;
    private String callBody;
    private String callId;
    private String homeOwnerMobileNumber;
    private String tenantMobileNumber;
    private String HomeOwnerCallStatus;
    private String tenantCallStatus;
    private String startDate;
    private String endDate;
    private boolean show = false;


    public Call(String callSubject, String callBody, String homeOwnerCallStatus, String tenantCallStatus, String startDate, String endDate) {
        this.subject = callSubject;
        this.callBody = callBody;
        this.HomeOwnerCallStatus = homeOwnerCallStatus;
        this.tenantCallStatus = tenantCallStatus;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public Call(String subject,String callBody,String startDate,String endDate,String homeOwnerMobileNumber){
        this.subject = subject;
        this.callBody = callBody;
        this.startDate = startDate;
        this.endDate = endDate;
        this.homeOwnerMobileNumber = homeOwnerMobileNumber;
    }

    public Call(String callId,String callSubject,String callBody,String homeOwnerCallStatus,String tenantCallStatus,String startDate,String endDate){
        this.callId = callId;
        this.subject = callSubject;
        this.callBody = callBody;
        this.HomeOwnerCallStatus =homeOwnerCallStatus;
        this.tenantCallStatus = tenantCallStatus;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getCallId(){
        return this.callId;
    }

    public static int getCounterForID() {
        return counterForID;
    }

    public String getSubject() {
        return subject;
    }

    public String getCallBody() {
        return callBody;
    }

    public String getHomeOwnerMobileNumber() {
        return homeOwnerMobileNumber;
    }

    public String getTenantMobileNumber() {
        return tenantMobileNumber;
    }

    public String getHomeOwnerCallStatus() {
        return HomeOwnerCallStatus;
    }

    public String getTenantCallStatus() {
        return tenantCallStatus;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void showInSearch(boolean show) {
        this.show = show;
    }
    public boolean isShow(){
        return show;
    }
}