package hk.gov.hyd.bunting.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Bunting {
    private long buntingId;
    @JsonProperty("Lamppost No.")
    private String lamppostNo;
    @JsonProperty("Department")
    private String department;
    @JsonProperty("Event")
    private String event;
    @JsonProperty("Application Date")
    private Date applicationDate;
    @JsonProperty("Installation Date")
    private Date installationDate;
    @JsonProperty("Removal Date")
    private Date removalDate;
    @JsonProperty("Registration Date")
    private Date registrationDate;
    @JsonProperty("Remarks")
    private String remark;
    @JsonProperty("Create Datetime")
    private Date createDatetime;
    @JsonProperty("Created By")
    private String createdBy;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }



    public long getBuntingId() {
        return buntingId;
    }

    public void setBuntingId(int buntingId) {
        this.buntingId = buntingId;
    }

    public String getLamppostNo() {
        return lamppostNo;
    }

    public void setLamppostNo(String lamppostNo) {
        this.lamppostNo = lamppostNo;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Date getInstallationDate() {
        return this.installationDate;
    }

    public void setInstallationDate(Date installationDate) {
        this.installationDate = installationDate;
    }

    public Date getRemovalDate() {
        return removalDate;
    }

    public void setRemovalDate(Date removalDate) {
        this.removalDate = removalDate;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }


}
