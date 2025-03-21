package hk.gov.hyd.bunting.misc;

import hk.gov.hyd.bunting.model.Bunting;
import hk.gov.hyd.bunting.model.Equipment;

import java.util.ArrayList;
import java.util.List;

public class ServiceResponse {
    private List<Equipment> newEquipments = new ArrayList<>();
    private List<Equipment> conflictEquipments = new ArrayList<>();
    private List<Bunting> newBuntings = new ArrayList<>();
    private List<Bunting> conflictBuntings = new ArrayList<>();
    private boolean isValidLamppostNo;
    private boolean isValidDateRange;

    public List<Bunting> getNewBuntings() {
        return newBuntings;
    }

    public void addNewBunting(Bunting bunting) {
        this.newBuntings.add(bunting);
    }

    public List<Bunting> getConflictBuntings() {
        return conflictBuntings;
    }


    public void addConflictBunting(Bunting bunting) {
        this.conflictBuntings.add(bunting);
    }

    public List<Equipment> getNewEquipments() {
        return newEquipments;
    }

    public void addNewEquipment(Equipment equipment) {
        this.newEquipments.add(equipment);
    }

    public List<Equipment> getConflictEquipments() {
        return conflictEquipments;
    }

    public void addConflictEquipment(Equipment equipment) {
        this.conflictEquipments.add(equipment);
    }

    public boolean notValidLamppostNo() {
        return !isValidLamppostNo;
    }

    public void setValidLamppostNo(boolean validLamppostNo) {
        isValidLamppostNo = validLamppostNo;
    }

    public boolean notValidDateRange() {
        return !isValidDateRange;
    }

    public void setValidDateRange(boolean validDateRange) {
        isValidDateRange = validDateRange;
    }

    public boolean isValidLamppostNo() {
        return isValidLamppostNo;
    }

    public boolean isValidDateRange() {
        return isValidDateRange;
    }
}
