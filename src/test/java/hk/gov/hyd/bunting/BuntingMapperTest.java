package hk.gov.hyd.bunting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import hk.gov.hyd.bunting.mapper.BuntingMapper;
import hk.gov.hyd.bunting.model.Bunting;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Transactional
public class BuntingMapperTest {

    @Autowired
    private BuntingMapper buntingMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeEach
    public void setUp() throws ParseException {
        assertNotNull(buntingMapper);


        // Insert record for lamppost no 'lamp001'
        Bunting bunting1 = new Bunting();
        bunting1.setLamppostNo("lamp001");
        bunting1.setDepartment("Organizer1");
        bunting1.setEvent("Event1");
        bunting1.setInstallationDate(dateFormat.parse("2025-01-01"));
        bunting1.setRemovalDate(dateFormat.parse("2025-01-31"));
        bunting1.setApplicationDate(dateFormat.parse("2024-12-01"));
        bunting1.setRegistrationDate(dateFormat.parse("2024-12-10"));
        bunting1.setRemark("");
        bunting1.setCreateDatetime(dateFormat.parse("2024-12-01"));
        buntingMapper.insertBunting(bunting1);

        // Insert record for lamppost no 'lamp002'
        Bunting bunting2 = new Bunting();
        bunting2.setLamppostNo("lamp002");
        bunting2.setDepartment("Organizer2");
        bunting2.setEvent("Event2");
        bunting2.setInstallationDate(dateFormat.parse("2025-02-01"));
        bunting2.setRemovalDate(dateFormat.parse("2025-02-28"));
        bunting2.setApplicationDate(dateFormat.parse("2024-12-15"));
        bunting2.setRegistrationDate(dateFormat.parse("2024-12-20"));
        bunting2.setRemark("");
        bunting2.setCreateDatetime(dateFormat.parse("2024-12-15"));
        buntingMapper.insertBunting(bunting2);
    }

    @Test
    public void testGetBuntingByLamppostNo() throws ParseException {
        List<Bunting> buntingList = buntingMapper.getBuntingByLamppostNo("lamp001");
        assertNotNull(buntingList);
        assertEquals(1, buntingList.size());
        assertEquals("lamp001", buntingList.getFirst().getLamppostNo());
    }

    @Test
    public void testGetAllBuntings() {
        List<Bunting> buntings = buntingMapper.getAllBuntings();
        assertNotNull(buntings);
        assertFalse(buntings.isEmpty());
    }

    @Test
    public void testGetBuntingByLamppostNoList() throws ParseException {
        List<String> lamppostNos = Arrays.asList("lamp001", "lamp002");
        List<Bunting> buntings = buntingMapper.getBuntingByLamppostNoList(lamppostNos);
        assertNotNull(buntings);
        assertFalse(buntings.isEmpty());
    }

    @Test
    public void testInsertBunting() throws ParseException {
        Bunting bunting = new Bunting();
        bunting.setLamppostNo("lamp003");
        bunting.setDepartment("Organizer");
        bunting.setEvent("Event");
        bunting.setInstallationDate(dateFormat.parse("2025-01-01"));
        bunting.setRemovalDate(dateFormat.parse("2025-01-31"));
        bunting.setApplicationDate(dateFormat.parse("2024-12-01"));
        bunting.setRegistrationDate(dateFormat.parse("2024-12-10"));
        bunting.setRemark("");
        bunting.setCreateDatetime(dateFormat.parse("2024-12-01 10:00:00"));

        buntingMapper.insertBunting(bunting);
        assertNotNull(bunting.getLamppostNo());
    }

    @Test
    public void testUpdateBunting() {
        List<Bunting> buntingList = buntingMapper.getBuntingByLamppostNo("lamp001");
        assertNotNull(buntingList);

        Bunting bunt = buntingList.getFirst();

        bunt.setEvent("Test Event");
        buntingMapper.updateBunting(bunt);

        List<Bunting> updatedBuntingList = buntingMapper.getBuntingByLamppostNo("lamp001");
        assertEquals("Test Event", updatedBuntingList.getFirst().getEvent());
    }

    @Test
    public void testDeleteBunting() {
        buntingMapper.deleteBunting(1);
        Bunting bunting = buntingMapper.getBuntingById(1);
        assertNull(bunting);
    }
}
