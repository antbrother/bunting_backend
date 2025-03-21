package hk.gov.hyd.bunting;

import hk.gov.hyd.bunting.mapper.BuntingMapper;
import hk.gov.hyd.bunting.mapper.EquipmentMapper;
import hk.gov.hyd.bunting.model.Bunting;
import hk.gov.hyd.bunting.model.Equipment;
import hk.gov.hyd.bunting.service.BuntingService;
import hk.gov.hyd.bunting.service.EquipmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Transactional
public class EquipmentServiceTest {

    @Autowired
    private EquipmentMapper equipmentMapper;

    @Autowired
    private EquipmentService equipmentService;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private int initRows=0;
    @BeforeEach
    public void setUp() throws ParseException {
        MockitoAnnotations.openMocks(this);

        assertNotNull(equipmentMapper);

        initRows = equipmentMapper.getAllEquipments().size();

        // Insert record for lamppost no 'lamp001'
        Equipment equipment1 = new Equipment();
        equipment1.setLamppostNo("lamp001");
        equipment1.setDepartment("Department1");
        equipment1.setInstallationDate(dateFormat.parse("2025-01-01"));
        equipment1.setRemovalDate(dateFormat.parse("2025-01-31"));
        equipment1.setDeviceType("Smart Device");
        equipment1.setPowerSupply("Y");
        equipment1.setRemark("Remark1");
        equipment1.setCreateDatetime(dateFormat.parse("2024-12-01"));
        equipmentMapper.insertEquipment(equipment1);

        // Verify insertion
        Equipment insertedEquipment1 = equipmentMapper.getEquipmentByLamppostNo("lamp001").getFirst();
        assertNotNull(insertedEquipment1, "Bunting with Lamppost No lamp001 should be inserted");

        // Insert record for lamppost no 'lamp002'
        Equipment equipment2 = new Equipment();
        equipment2.setLamppostNo("lamp002");
        equipment2.setDepartment("Department2");
        equipment2.setInstallationDate(dateFormat.parse("2025-01-01"));
        equipment2.setRemovalDate(dateFormat.parse("2025-01-31"));
        equipment2.setDeviceType("Smart Device");
        equipment2.setPowerSupply("Y");
        equipment2.setRemark("Remark1");
        equipment2.setCreateDatetime(dateFormat.parse("2024-12-15"));
        equipmentMapper.insertEquipment(equipment2);

        // Verify insertion
        Equipment insertedEquipment2 = equipmentMapper.getEquipmentByLamppostNo("lamp002").getFirst();
        assertNotNull(insertedEquipment2, "Bunting with Lamppost No lamp002 should be inserted");


    }

    @Test
    public void testGetAllEquipments() {
        List<Equipment> result = equipmentService.getAllEquipments();
        assertNotNull(result);
        assertEquals(2, result.size() - initRows);
    }

    @Test
    public void testGetEquipmentByLamppostNo() {
        Equipment result = equipmentService.getEquipmentByLamppostNo("lamp001").getFirst();
        assertNotNull(result);
        assertEquals("lamp001", result.getLamppostNo());
    }

    @Test
    public void testGetEquipmentByLamppostNoList() {
        List<Equipment> result = equipmentService.getEquipmentByLamppostNoList("lamp001, lamp002");
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testCreateEquipment() throws ParseException {
        List<Equipment> result_before = equipmentService.getAllEquipments();

        Equipment equipment = new Equipment();
        equipment.setLamppostNo("lamp003");
        equipment.setDepartment("Department3");
        equipment.setInstallationDate(dateFormat.parse("2025-01-01"));
        equipment.setRemovalDate(dateFormat.parse("2025-01-31"));
        equipment.setDeviceType("Smart Device");
        equipment.setPowerSupply("Y");
        equipment.setRemark("Remark3");
        equipment.setCreateDatetime(dateFormat.parse("2024-12-01"));
        equipmentService.createEquipment(equipment);

        List<Equipment> result_after = equipmentService.getAllEquipments();

        assertEquals(1, result_after.size()-result_before.size());
    }



    @Test
    public void testUpdateEquipment() throws ParseException {
        Equipment equipment = equipmentMapper.getEquipmentByLamppostNo("lamp001").getFirst();
        Equipment equipment1 = new Equipment();
        equipment1.setLamppostNo("lamp004");
        equipment1.setDepartment("New Department");
        equipment1.setInstallationDate(dateFormat.parse("2025-01-01"));
        equipment1.setRemovalDate(dateFormat.parse("2025-01-31"));
        equipment1.setDeviceType("Smart Device");
        equipment1.setPowerSupply("Y");
        equipment1.setRemark("New Remark");
        equipment1.setCreateDatetime(dateFormat.parse("2024-12-01"));

        equipmentService.updateEquipment(equipment.getEquipmentId(), equipment1);

        Equipment updatedEquipment = equipmentService.getEquipmentByLamppostNo("lamp004").getFirst();
        assertEquals("lamp004", updatedEquipment.getLamppostNo());
        assertEquals("New Department", updatedEquipment.getDepartment());
        assertEquals("New Remark", updatedEquipment.getRemark());
    }

    @Test
    public void testDeleteEquipment() {
        List<Long> ids = new ArrayList<Long>();
        ids.add(equipmentMapper.getEquipmentByLamppostNo("lamp001").getFirst().getEquipmentId());
        equipmentService.deleteEquipments(ids);
        List<Equipment> resultList = equipmentService.getEquipmentByLamppostNo("lamp001");
        assertEquals(0, resultList.size());
    }

}

