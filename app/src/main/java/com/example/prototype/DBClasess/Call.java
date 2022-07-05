package com.example.prototype.DBClasess;

import java.io.File;
import java.util.Date;

public class Call {
    static int counterForID = 0;

    private String id;
    private String landLordID;
    private String tenantID;
    private String landLordMode;
    private String tenantMode;
    private Date commitDate;
    private Date endDate;
    private String title;
    private String callText;
    private File file = null;

    public Call() {
        setId();
    }

    public Call(String landLordID, String tenantID, String title, String callText) {
        setId();
        setLandLordID(landLordID);
        setTenantID(tenantID);

        setLandLordMode("open");
        setTenantMode("open");

        setCommiteDate();
        setEndDate(null);

        setTitle(title);
        setCallText(callText);

        setFile(null);
    }

    private void setCommiteDate() {
        this.commitDate = new Date();
    }

    public Date getCommiteDate() {
        return commitDate;
    }

    public String getId() {
        return id;
    }

    public void setId() {
        this.counterForID++;
        this.id = String.valueOf(counterForID);
    }

    public String getLandLordID() {
        return landLordID;
    }

    public void setLandLordID(String landLordID) {
        this.landLordID = landLordID;
    }

    public String getTenantID() {
        return tenantID;
    }

    public void setTenantID(String tenantID) {
        this.tenantID = tenantID;
    }

    public String getLandLordMode() {
        return landLordMode;
    }

    public void setLandLordMode(String landLordMode) {
        this.landLordMode = landLordMode;
    }

    public String getTenantMode() {
        return tenantMode;
    }

    public void setTenantMode(String tenantMode) {
        this.tenantMode = tenantMode;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCallText() {
        return callText;
    }

    public void setCallText(String callText) {
        this.callText = callText;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "Call{" +
                "id='" + id + '\'' +
                ", landLordID='" + landLordID + '\'' +
                ", tenantID='" + tenantID + '\'' +
                ", landLordMode='" + landLordMode + '\'' +
                ", tenantMode='" + tenantMode + '\'' +
                ", commitDate=" + commitDate +
                ", endDate=" + endDate +
                ", title='" + title + '\'' +
                ", callText='" + callText + '\'' +
                ", file=" + file +
                '}';
    }

}