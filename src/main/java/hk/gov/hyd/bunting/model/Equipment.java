package hk.gov.hyd.bunting.model;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Equipment {
    private long equipmentId;
    @JsonProperty("Lamppost No.")
    private String lamppostNo;
    @JsonProperty("Department")
    private String department;
    @JsonProperty("Device Type")
    private String deviceType;
    @JsonProperty("Installation Date")
    private Date installationDate;
    @JsonProperty("Removal Date")
    private Date removalDate;
    @JsonProperty("Power Supply")
    private String powerSupply;
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

    public long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getLamppostNo() {
        return lamppostNo;
    }

    public void setLamppostNo(String lamppostNo) {
        this.lamppostNo = lamppostNo;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Date getInstallationDate() {
        return installationDate;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPowerSupply() {
        return powerSupply;
    }

    public void setPowerSupply(String powerSupply) {
        this.powerSupply = powerSupply;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

}
