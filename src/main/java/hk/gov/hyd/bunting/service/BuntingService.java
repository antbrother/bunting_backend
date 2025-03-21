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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuntingService {

    private final BuntingMapper buntingMapper;
    private final EquipmentMapper equipmentMapper;

    @Autowired
    public BuntingService(BuntingMapper buntingMapper, EquipmentMapper equipmentMapper) {
        this.buntingMapper = buntingMapper;
        this.equipmentMapper = equipmentMapper;
    }

    public List<Bunting> getAllBuntings() {
        return buntingMapper.getAllBuntings();
    }

    public Bunting getBuntingById(long id) {
        return buntingMapper.getBuntingById(id);
    }

    public List<Bunting> getBuntingByLamppostNo(String lamppostNo) {
        return buntingMapper.getBuntingByLamppostNo(lamppostNo);
    }

    public List<Bunting> getBuntingByEvent(String event) {
        return buntingMapper.getBuntingsByCriteria("event", event);
    }

    public List<Bunting> getBuntingByLamppostNoAndDateRange(String lamppostNo, LocalDate startDate, LocalDate endDate) {
        return buntingMapper.getBuntingByLamppostNoAndDateRange(lamppostNo, startDate, endDate);
    }

    public List<Bunting> getBuntingByEventAndDateRange(String event, LocalDate startDate, LocalDate endDate) {
        return buntingMapper.getBuntingByEventAndDateRange(event, startDate, endDate);
    }

    public List<Bunting> getBuntingByDateRange(LocalDate startDate, LocalDate endDate) {
        return buntingMapper.getBuntingByDateRange(startDate, endDate);
    }


    public List<Bunting> getBuntingsByLamppostNoList(String lamppostNoList) {
        List<String> lamppostList = Arrays.stream(lamppostNoList.split(","))
                .map(String::trim) // To remove leading/trailing spaces
                .toList();

        return buntingMapper.getBuntingByLamppostNoList(lamppostList);
    }

    public void createBunting(Bunting bunting) {
        bunting.setCreateDatetime(new Date());
        buntingMapper.insertBunting(bunting);
    }

    @Transactional
    public ServiceResponse createBuntingList(List<Bunting> buntingList) {
        boolean conflictEquipmentFind = false;
        boolean conflictBuntingFind = false;

        // Extract lamppost numbers from incoming equipment list
        List<String> lamppostNoList = buntingList.stream()
                .map(Bunting::getLamppostNo)
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
        for (Bunting bunting : buntingList) {
            for (Bunting existingBunting : existingBuntings) {
                if (bunting.getLamppostNo().equals(existingBunting.getLamppostNo())) {
                    if (isOverlapping(existingBunting, bunting)) {
                        serviceResponse.addConflictBunting(existingBunting);
                        conflictBuntingFind = true;
                    }
                }
            }
            for (Equipment existingEquipment : existingEquipments) {
                if (bunting.getLamppostNo().equals(existingEquipment.getLamppostNo())) {
                    if (isOverlapping(existingEquipment, bunting)) {
                        serviceResponse.addConflictEquipment(existingEquipment);
                        conflictEquipmentFind = true;
                    }
                }
            }
            if (LampPostValidator.notValidDateRange(bunting.getInstallationDate(), bunting.getRemovalDate())) {
                serviceResponse.setValidDateRange(false);
            }
        }


        if (conflictEquipmentFind || conflictBuntingFind || serviceResponse.notValidLamppostNo() || serviceResponse.notValidDateRange()) {
            return serviceResponse;
        }

        // If no duplicates, insert all new equipment
        for (Bunting bunting : buntingList) {
            bunting.setCreateDatetime(new Date());
            buntingMapper.insertBunting(bunting);
            serviceResponse.addNewBunting(bunting);
        }

        return serviceResponse;
    }



    private boolean isOverlapping(Bunting existing, Bunting uploaded) {
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

    private boolean isOverlapping(Equipment existing, Bunting uploaded) {
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

    public List<Bunting> retrieveData(QueryObject queryObject) {
        if (!validateQueryObject(queryObject)) {
            throw new IllegalArgumentException("Invalid query parameters."); // Or handle as needed
        }
        String criteria = queryObject.getCriteria().toLowerCase().replace(" ", "_");
        String criteriaColumn = criteria.equals("lamppost_no.") ? "lamppost_no" : criteria;

        if (queryObject.getCriteria().contains("Date")) {
            return buntingMapper.getBuntingsByDateRange(criteriaColumn, queryObject.getFromDate(), queryObject.getToDate());
        } else {
            if (queryObject.isExactMatch()) {
                return buntingMapper.getBuntingsByExactCriteria(criteriaColumn, queryObject.getInputData());
            } else {
                return buntingMapper.getBuntingsByCriteria(criteriaColumn, queryObject.getInputData());
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


    public void updateBunting(long id, Bunting buntingDetails) {
        Bunting bunting = buntingMapper.getBuntingById(id);
        if (bunting != null) {
            bunting.setLamppostNo(buntingDetails.getLamppostNo());
            bunting.setDepartment(buntingDetails.getDepartment());
            bunting.setEvent(buntingDetails.getEvent());
            bunting.setInstallationDate(buntingDetails.getInstallationDate());
            bunting.setRemovalDate(buntingDetails.getRemovalDate());
            bunting.setApplicationDate(buntingDetails.getApplicationDate());
            bunting.setRegistrationDate(buntingDetails.getRegistrationDate());
            bunting.setRemark(buntingDetails.getRemark());
            bunting.setCreatedBy(buntingDetails.getCreatedBy());
            buntingMapper.updateBunting(bunting);
        } else {
            throw new RuntimeException("Bunting not found");
        }
    }

    public void deleteBuntings(List<Long> ids) {
        for (Long id : ids) {
            buntingMapper.deleteBunting(id);
        }
    }

    public void deleteBunting(long id) {
        buntingMapper.deleteBunting(id);
    }



}
