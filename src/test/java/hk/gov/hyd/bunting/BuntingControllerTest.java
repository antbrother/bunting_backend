package hk.gov.hyd.bunting;
import com.fasterxml.jackson.databind.ObjectMapper;
import hk.gov.hyd.bunting.controller.BuntingController;
import hk.gov.hyd.bunting.model.Bunting;
import hk.gov.hyd.bunting.service.BuntingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BuntingController.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BuntingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BuntingService buntingService;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new BuntingController(buntingService)).build();
    }


    @Test
    public void testGetAllBuntings() throws Exception {
        // Mock the service response
        when(buntingService.getAllBuntings()).thenReturn(List.of(new Bunting()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/buntings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }


    @Test
    public void testGetBuntingById() throws Exception {
        // Mock the service response
        Bunting mockBunting = new Bunting();
        mockBunting.setBuntingId(1);
        when(buntingService.getBuntingById(1)).thenReturn(mockBunting);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/buntings/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.buntingId").value(1));
    }

    @Test
    public void testCreateBunting() throws Exception {
        {
            // Prepare the mock Bunting object
            Bunting mockBunting = new Bunting();
            mockBunting.setBuntingId(1); // Ensure the mockBunting has an ID set

            // Mock the service method call
            doNothing().when(buntingService).createBunting(mockBunting);

            mockMvc.perform(MockMvcRequestBuilders.post("/api/buntings")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(mockBunting)))
                    .andExpect(status().isOk());

            // Capture the argument passed to the createBunting method
            ArgumentCaptor<Bunting> buntingCaptor = ArgumentCaptor.forClass(Bunting.class);
            verify(buntingService).createBunting(buntingCaptor.capture());

            // Verify the properties of the captured argument
            Bunting capturedBunting = buntingCaptor.getValue();
            assertEquals(mockBunting.getBuntingId(), capturedBunting.getBuntingId());

        }

    }

    @Test
    public void testUpdateBunting () throws Exception {
        // Mock the service response
        Bunting mockBunting = new Bunting();
        mockBunting.setBuntingId(1); // Ensure the mockBunting has an ID set

        // Mock the service method call
        doNothing().when(buntingService).createBunting(mockBunting);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/buntings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(mockBunting)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.buntingId").exists());
    }

    @Test
    public void testDeleteBunting () throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/buntings/delete?ids=1"))
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