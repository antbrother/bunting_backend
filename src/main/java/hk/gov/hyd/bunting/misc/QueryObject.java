package hk.gov.hyd.bunting.misc;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class QueryObject {
    @JsonProperty("type")
    String type;
    @JsonProperty("criteria")
    String criteria;
    @JsonProperty("inputData")
    String inputData;
    @JsonProperty("fromDate")
    Date fromDate;
    @JsonProperty("toDate")
    Date toDate;
    @JsonProperty("exactMatch")
    boolean exactMatch;


    public boolean isExactMatch() {
        return exactMatch;
    }

    public void setExactMatch(boolean exactMatch) {
        this.exactMatch = exactMatch;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
}
