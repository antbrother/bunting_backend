package hk.gov.hyd.bunting;
import hk.gov.hyd.bunting.mapper.BuntingMapper;
import hk.gov.hyd.bunting.model.Bunting;
import hk.gov.hyd.bunting.service.BuntingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Transactional
public class BuntingServiceTest {

    @Autowired
    private BuntingMapper buntingMapper;

    @Autowired
    private BuntingService buntingService;

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
        bunting1.setCreateDatetime(dateFormat.parse("2024-12-01"));
        buntingMapper.insertBunting(bunting1);

        // Verify insertion
        Bunting insertedBunting1 = buntingMapper.getBuntingByLamppostNo("lamp001").getFirst();
        assertNotNull(insertedBunting1, "Bunting with Lamppost No lamp001 should be inserted");

        // Insert record for lamppost no 'lamp002'
        Bunting bunting2 = new Bunting();
        bunting2.setLamppostNo("lamp002");
        bunting2.setDepartment("Organizer2");
        bunting2.setEvent("Event2");
        bunting2.setInstallationDate(dateFormat.parse("2025-02-01"));
        bunting2.setRemovalDate(dateFormat.parse("2025-02-28"));
        bunting2.setApplicationDate(dateFormat.parse("2024-12-15"));
        bunting2.setRegistrationDate(dateFormat.parse("2024-12-20"));
        bunting2.setCreateDatetime(dateFormat.parse("2024-12-15"));
        buntingMapper.insertBunting(bunting2);

        // Verify insertion
        Bunting insertedBunting2 = buntingMapper.getBuntingByLamppostNo("lamp002").getFirst();
        assertNotNull(insertedBunting2, "Bunting with Lamppost No lamp002 should be inserted");
    }
/*
    @Test
    public void testGetAllBuntings() {
        List<Bunting> result = buntingService.getAllBuntings();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

     @Test
    public void testGetBuntingByLamppostNo() {
        Bunting result = buntingService.getBuntingByLamppostNo("lamp001").getFirst();
        assertNotNull(result);
        assertEquals("lamp001", result.getLamppostNo());
    }

    @Test
    public void testGetBuntingsByLamppostNoList() {
        List<Bunting> result = buntingService.getBuntingsByLamppostNoList("lamp001, lamp002");
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testCreateBunting() throws ParseException {
        Bunting bunting = new Bunting();
        bunting.setLamppostNo("lamp003");
        bunting.setDepartment("Organizer3");
        bunting.setEvent("Event3");
        bunting.setInstallationDate(dateFormat.parse("2025-03-01"));
        bunting.setRemovalDate(dateFormat.parse("2025-03-31"));
        bunting.setApplicationDate(dateFormat.parse("2024-12-25"));
        bunting.setRegistrationDate(dateFormat.parse("2024-12-30"));

        buntingService.createBunting(bunting);

        List<Bunting> result = buntingService.getAllBuntings();
        assertEquals(3, result.size());
    }

    @Test
    public void testUpdateBunting() throws ParseException {
        Bunting bunt = buntingMapper.getBuntingByLamppostNo("lamp001").getFirst();
        Bunting buntingDetails = new Bunting();
        buntingDetails.setLamppostNo("lamp004");
        buntingDetails.setDepartment("New Organizer");
        buntingDetails.setEvent("New Event");
        buntingDetails.setInstallationDate(dateFormat.parse("2025-04-01"));
        buntingDetails.setRemovalDate(dateFormat.parse("2025-04-30"));
        buntingDetails.setApplicationDate(dateFormat.parse("2024-12-10"));
        buntingDetails.setRegistrationDate(dateFormat.parse("2024-12-20"));

        buntingService.updateBunting(bunt.getBuntingId(), buntingDetails);

        Bunting updatedBunting = buntingService.getBuntingByLamppostNo("lamp004").getFirst();
        assertEquals("lamp004", updatedBunting.getLamppostNo());
        assertEquals("New Organizer", updatedBunting.getDepartment());
        assertEquals("New Event", updatedBunting.getEvent());
    }

    @Test
    public void testDeleteBunting() {
        List<Long> ids = new ArrayList<Long>();
        ids.add(buntingMapper.getBuntingByLamppostNo("lamp001").getFirst().getBuntingId());
        buntingService.deleteBuntings(ids);
        List<Bunting> resultList = buntingService.getBuntingByLamppostNo("lamp001");
        assertEquals(0, resultList.size());
    }

 */
}
