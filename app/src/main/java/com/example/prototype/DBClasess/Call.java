package com.example.prototype.DBClasess;

import java.io.File;
import java.util.Date;

public class Call {
    static int counterForID = 0;

    private String subject;
    private String callBody;
    private String homeOwnerMobileNumber;
    private String tenantMobileNumber;
    private String HomeOwnerCallStatus;
    private String tenantCallStatus;
    private String startDate;
    private String endDate;



    public Call(String callSubject, String callBody, String homeOwnerCallStatus,String tenantCallStatus, String startDate, String endDate) {
        this.subject = callSubject;
        this.callBody = callBody;
        this.HomeOwnerCallStatus = homeOwnerCallStatus;
        this.tenantCallStatus = tenantCallStatus;
        this.startDate = startDate;
        this.endDate = endDate;
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
}