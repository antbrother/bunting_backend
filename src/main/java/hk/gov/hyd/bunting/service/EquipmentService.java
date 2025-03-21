package hk.gov.hyd.bunting.service;

import hk.gov.hyd.bunting.mapper.BuntingMapper;
import hk.gov.hyd.bunting.mapper.EquipmentMapper;
import hk.gov.hyd.bunting.misc.LampPostValidator;
import hk.gov.hyd.bunting.misc.QueryObject;
import hk.gov.hyd.bunting.misc.ServiceResponse;
import hk.gov.hyd.bunting.model.Bunting;
import hk.gov.hyd.bunting.model.Equipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipmentService {

    private final EquipmentMapper equipmentMapper;
    private final BuntingMapper buntingMapper;

    @Autowired
    public EquipmentService(EquipmentMapper equipmentMapper, BuntingMapper buntingMapper) {
        this.buntingMapper = buntingMapper;
        this.equipmentMapper = equipmentMapper;
    }
    public List<Equipment> getAllEquipments() {
        return equipmentMapper.getAllEquipments();
    }

    public Equipment getEquipmentById(int id) {
        return equipmentMapper.getEquipmentById(id);
    }

    public List<Equipment> getEquipmentByLamppostNo(String lamppostNo) {
        return equipmentMapper.getEquipmentByLamppostNo(lamppostNo);
    }

    public List<Equipment> getEquipmentByLamppostNoList(String lamppostNoList) {
        List<String> lamppostList = Arrays.stream(lamppostNoList.split(","))
                .map(String::trim) // To remove leading/trailing spaces
                .toList();

        return equipmentMapper.getEquipmentByLamppostNoList(lamppostList);
    }

    public void createEquipment(Equipment equipment) {
        equipmentMapper.insertEquipment(equipment);
    }

    @Transactional
    public ServiceResponse createEquipmentList(List<Equipment> equipmentList) {
        boolean conflictEquipmentFind = false;
        boolean conflictBuntingFind = false;

        // Extract lamppost numbers from incoming equipment list
        List<String> lamppostNoList = equipmentList.stream()
                .map(Equipment::getLamppostNo)
                .collect(Collectors.toList());
        // Create the response object
        ServiceResponse serviceResponse = new ServiceResponse();

        // initialize
        serviceResponse.setValidLamppostNo(true);
        serviceResponse.setValidDateRange(true);

        serviceResponse.setValidLamppostNo(LampPostValidator.isValidLampPostNoList(lamppostNoList));

        // Fetch existing equipment and bunting by lamppost numbers
        List<Equipment> existingEquipments = equipmentMapper.getEquipmentByLamppostNoList(lamppostNoList);
        List<Bunting> existingBuntings = buntingMapper.getBuntingByLamppostNoList(lamppostNoList);


        // Check for overlapping equipment
        for (Equipment equipment : equipmentList) {
            for (Equipment existingEquipment : existingEquipments) {
                if (equipment.getLamppostNo().equals(existingEquipment.getLamppostNo())) {
                    if (isOverlapping(existingEquipment, equipment)) {
                        serviceResponse.addConflictEquipment(existingEquipment);
                        conflictEquipmentFind = true;
                    }
                }
            }
            for (Bunting existingBunting : existingBuntings) {
                if (equipment.getLamppostNo().equals(existingBunting.getLamppostNo())) {
                    if (isOverlapping(existingBunting, equipment)) {
                        serviceResponse.addConflictBunting(existingBunting);
                        conflictBuntingFind = true;
                    }
                }
            }
            if (LampPostValidator.notValidDateRange(equipment.getInstallationDate(), equipment.getRemovalDate())) {
                serviceResponse.setValidDateRange(false);
            }
        }


        if (conflictEquipmentFind || conflictBuntingFind || serviceResponse.notValidLamppostNo() || serviceResponse.notValidDateRange()) {
            return serviceResponse;
        }

        // If no duplicates, insert all new equipment
        for (Equipment equipment : equipmentList) {
            equipment.setCreateDatetime(new Date());
            equipmentMapper.insertEquipment(equipment);
            serviceResponse.addNewEquipment(equipment);
        }

        return serviceResponse;
    }


    private boolean isOverlapping(Equipment existing, Equipment uploaded) {
        if (existing.getRemovalDate() == null) {
            // open-end for existing here
            if (uploaded.getRemovalDate() == null) {
                // both are open-end here.....
                return true;
            } else {
                // closed-end for uploaded and open-end for existing here
                return existing.getInstallationDate().compareTo(uploaded.getRemovalDate()) <= 0;
            }
        } else {
            // closed-end for existing here
            if (uploaded.getRemovalDate() == null) {
                // open-end for uploaded and closed-end for existing here
                return existing.getRemovalDate().compareTo(uploaded.getInstallationDate()) >= 0;
            } else {
                // both are close-end here
                if ((uploaded.getInstallationDate().compareTo(existing.getInstallationDate()) == 0) || (uploaded.getInstallationDate().compareTo(existing.getRemovalDate()) == 0) || (uploaded.getRemovalDate().compareTo(existing.getInstallationDate()) == 0) || (uploaded.getRemovalDate().compareTo(existing.getRemovalDate()) ==0)) {
                    return true;
                } else {
                    if (uploaded.getInstallationDate().compareTo(existing.getInstallationDate()) < 0) {
                        return uploaded.getRemovalDate().compareTo(existing.getInstallationDate()) > 0;
                    } else {
                        return existing.getRemovalDate().compareTo(uploaded.getInstallationDate()) > 0;
                    }
                }
            }
        }
    }

    private boolean isOverlapping(Bunting existing, Equipment uploaded) {
        if (existing.getRemovalDate() == null) {
            // open-end for existing here
            if (uploaded.getRemovalDate() == null) {
                // both are open-end here.....
                return true;
            } else {
                // closed-end for uploaded and open-end for existing here
                return existing.getInstallationDate().compareTo(uploaded.getRemovalDate()) <= 0;
            }
        } else {
            // closed-end for existing here
            if (uploaded.getRemovalDate() == null) {
                // open-end for uploaded and closed-end for existing here
                return existing.getRemovalDate().compareTo(uploaded.getInstallationDate()) >= 0;
            } else {
                // both are close-end here
                if ((uploaded.getInstallationDate().compareTo(existing.getInstallationDate()) == 0) || (uploaded.getInstallationDate().compareTo(existing.getRemovalDate()) == 0) || (uploaded.getRemovalDate().compareTo(existing.getInstallationDate()) == 0) || (uploaded.getRemovalDate().compareTo(existing.getRemovalDate()) ==0)) {
                    return true;
                } else {
                    if (uploaded.getInstallationDate().compareTo(existing.getInstallationDate()) < 0) {
                        return uploaded.getRemovalDate().compareTo(existing.getInstallationDate()) > 0;
                    } else {
                        return existing.getRemovalDate().compareTo(uploaded.getInstallationDate()) > 0;
                    }
                }
            }
        }
    }

    public List<Equipment> retrieveData(QueryObject queryObject) {
        if (!validateQueryObject(queryObject)) {
            throw new IllegalArgumentException("Invalid query parameters."); // Or handle as needed
        }
        String criteria = queryObject.getCriteria().toLowerCase().replace(" ", "_");
        String criteriaColumn = criteria.equals("lamppost_no.") ? "lamppost_no" : criteria;

        if (queryObject.getCriteria().contains("Date")) {
            return equipmentMapper.getEquipmentsByDateRange(criteriaColumn, queryObject.getFromDate(), queryObject.getToDate());
        } else {
            if (queryObject.isExactMatch()) {
                return equipmentMapper.getEquipmentsByExactCriteria(criteriaColumn, queryObject.getInputData());
            } else {
                return equipmentMapper.getEquipmentsByCriteria(criteriaColumn, queryObject.getInputData());
            }
        }
    }

    private boolean validateQueryObject(QueryObject queryObject) {
// Check if type is valid
        if (queryObject.getType() == null || queryObject.getType().isEmpty()) {
            return false; // Type is required
        }

        // Check if criteria is valid
        if (queryObject.getCriteria() == null || queryObject.getCriteria().isEmpty()) {
            return false; // Criteria is required
        }

        // Check if inputData is required based on criteria
        if (!queryObject.getCriteria().contains("Date") &&
                (queryObject.getInputData() == null || queryObject.getInputData().isEmpty())) {
            return false; // inputData is required for non-date criteria
        }

        // Check date range validity
        if (queryObject.getFromDate() != null && queryObject.getToDate() != null) {
            return !queryObject.getFromDate().after(queryObject.getToDate()); // fromDate must be before toDate
        }

        // If all checks pass, return true
        return true;
    }


    public void updateEquipment(long id, Equipment equipmentDetails) {
        Equipment equipment = equipmentMapper.getEquipmentById(id);
        if (equipment != null) {
            equipment.setLamppostNo(equipmentDetails.getLamppostNo());
            equipment.setDeviceType(equipmentDetails.getDeviceType());
            equipment.setInstallationDate(equipmentDetails.getInstallationDate());
            equipment.setRemovalDate(equipmentDetails.getRemovalDate());
            equipment.setDepartment(equipmentDetails.getDepartment());
            equipment.setPowerSupply(equipmentDetails.getPowerSupply());
            equipment.setRemark(equipmentDetails.getRemark());
            equipment.setCreatedBy(equipmentDetails.getCreatedBy());
            equipmentMapper.updateEquipment(equipment);
        } else {
            throw new RuntimeException("Equipment not found");
        }
    }

    public void deleteEquipments(List<Long> ids) {
        for (Long id : ids) {
            equipmentMapper.deleteEquipment(id);
        }
    }

    public void deleteEquipment(Long id) {
        equipmentMapper.deleteEquipment(id);
    }
}


