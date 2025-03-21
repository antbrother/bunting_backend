package hk.gov.hyd.bunting;

import com.fasterxml.jackson.databind.ObjectMapper;
import hk.gov.hyd.bunting.controller.BuntingController;
import hk.gov.hyd.bunting.controller.EquipmentController;
import hk.gov.hyd.bunting.model.Bunting;
import hk.gov.hyd.bunting.model.Equipment;
import hk.gov.hyd.bunting.service.EquipmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EquipmentController.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class EquipmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EquipmentService equipmentService;

    private List<Equipment> mockEquipments;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new EquipmentController(equipmentService)).build();

        Equipment equipment1 = new Equipment();
        equipment1.setEquipmentId(1);
        equipment1.setLamppostNo("lamp001");
        equipment1.setDeviceType("Type1");

        Equipment equipment2 = new Equipment();
        equipment2.setEquipmentId(2);
        equipment2.setLamppostNo("lamp002");
        equipment2.setDeviceType("Type2");

        mockEquipments = Arrays.asList(equipment1, equipment2);

    }

    @Test
    public void testGetAllEquipments() throws Exception {
        // Mock the service response
        when(equipmentService.getAllEquipments()).thenReturn(List.of(new Equipment()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/equipments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetEquipmentById() throws Exception {
        // Mock the service response
        Equipment mockEquipment = new Equipment();
        mockEquipment.setEquipmentId(1);
        when(equipmentService.getEquipmentById(1)).thenReturn(mockEquipment);

        System.out.println("Test: Mocking service to return equipment with ID: " + mockEquipment.getEquipmentId());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/equipments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.equipmentId").value(1));
    }

    @Test
    public void testCreateEquipment() throws Exception {
        {
            // Prepare the mock Bunting object
            Equipment mockEquipment = new Equipment();
            mockEquipment.setEquipmentId(1); // Ensure the mockBunting has an ID set

            // Mock the service method call
            doNothing().when(equipmentService).createEquipment(mockEquipment);

            mockMvc.perform(MockMvcRequestBuilders.post("/api/equipments")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(mockEquipment)))
                    .andExpect(status().isOk());

            // Capture the argument passed to the createBunting method
            ArgumentCaptor<Equipment> equipmentCaptor = ArgumentCaptor.forClass(Equipment.class);
            verify(equipmentService).createEquipment(equipmentCaptor.capture());

            // Verify the properties of the captured argument
            Equipment capturedEquipment = equipmentCaptor.getValue();
            assertEquals(mockEquipment.getEquipmentId(), capturedEquipment.getEquipmentId());

        }

    }

    @Test
    public void testUpdateEquipment () throws Exception {
        // Mock the service response
        Equipment mockEquipment = new Equipment();
        mockEquipment.setEquipmentId(1); // Ensure the mockBunting has an ID set

        // Mock the service method call
        doNothing().when(equipmentService).createEquipment(mockEquipment);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/equipments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(mockEquipment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.equipmentId").exists());
    }

    @Test
    public void testDeleteEquipment () throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/equipments/delete?ids=1"))
                .andExpect(status().isNoContent());
    }




    // Utility method to convert object to JSON string
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
