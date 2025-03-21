package hk.gov.hyd.bunting;

import hk.gov.hyd.bunting.mapper.EquipmentMapper;
import hk.gov.hyd.bunting.model.Bunting;
import hk.gov.hyd.bunting.model.Equipment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // This ensures that Spring Boot does not replace your configured database with an embedded one
public class EquipmentMapperTest {

    @Autowired
    private EquipmentMapper equipmentMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeEach
    public void setUp() throws ParseException {
        assertNotNull(equipmentMapper);

        // Insert record for lamppost no 'lamp001'
        Equipment equipment1 = new Equipment();
        equipment1.setLamppostNo("lamp001");
        equipment1.setDeviceType("Type1");
        equipment1.setInstallationDate(dateFormat.parse("2025-01-01"));
        equipment1.setRemovalDate(dateFormat.parse("2025-01-31"));
        equipment1.setDepartment("Department1");
        equipment1.setPowerSupply("Y");
        equipment1.setRemark("Remark1");
        equipment1.setCreateDatetime(dateFormat.parse("2024-12-01"));
        equipmentMapper.insertEquipment(equipment1);

        // Insert record for lamppost no 'lamp002'
        Equipment equipment2 = new Equipment();
        equipment2.setLamppostNo("lamp002");
        equipment2.setDeviceType("Type2");
        equipment2.setInstallationDate(dateFormat.parse("2025-02-01"));
        equipment2.setRemovalDate(dateFormat.parse("2025-02-28"));
        equipment2.setDepartment("Department2");
        equipment2.setPowerSupply("Y");
        equipment2.setRemark("Remark2");
        equipment2.setCreateDatetime(dateFormat.parse("2024-12-15"));
        equipmentMapper.insertEquipment(equipment2);
    }

    @Test
    public void testGetEquipmentByLamppostNo() {
        Equipment equipment = equipmentMapper.getEquipmentByLamppostNo("lamp001").getFirst();
        assertNotNull(equipment);
        assertEquals("lamp001", equipment.getLamppostNo());
    }

    @Test
    public void testGetAllEquipments() {
        List<Equipment> equipments = equipmentMapper.getAllEquipments();
        assertNotNull(equipments);
        assertFalse(equipments.isEmpty());
    }

    @Test
    public void testGetEquipmentByLamppostNoList() throws ParseException {
        List<String> lamppostNos = Arrays.asList("lamp001", "lamp002");
        List<Equipment> equipments = equipmentMapper.getEquipmentByLamppostNoList(lamppostNos);
        assertNotNull(equipments);
        assertFalse(equipments.isEmpty());
    }

    @Test
    public void testInsertEquipment() throws ParseException {
        Equipment equipment = new Equipment();
        equipment.setLamppostNo("lamp003");
        equipment.setDeviceType("Type3");
        equipment.setInstallationDate(dateFormat.parse("2025-03-01"));
        equipment.setRemovalDate(dateFormat.parse("2025-03-31"));
        equipment.setDepartment("Department3");
        equipment.setPowerSupply("Y");
        equipment.setRemark("Remark3");
        equipment.setCreateDatetime(dateFormat.parse("2024-12-25"));

        equipmentMapper.insertEquipment(equipment);
        assertNotNull(equipment.getLamppostNo());
    }

    @Test
    public void testUpdateEquipment() {
        Equipment equipment = equipmentMapper.getEquipmentByLamppostNo("lamp001").getFirst();
        assertNotNull(equipment);

        equipment.setDeviceType("UpdatedType");
        equipmentMapper.updateEquipment(equipment);

        Equipment updatedEquipment = equipmentMapper.getEquipmentByLamppostNo("lamp001").getFirst();
        assertEquals("UpdatedType", updatedEquipment.getDeviceType());
    }

    @Test
    public void testDeleteEquipment() {
        equipmentMapper.deleteEquipment(1);
        Equipment equipment = equipmentMapper.getEquipmentById(1);
        assertNull(equipment);
    }
}

