package hk.gov.hyd.bunting.controller;

import hk.gov.hyd.bunting.misc.QueryObject;
import hk.gov.hyd.bunting.model.Equipment;
import hk.gov.hyd.bunting.service.EquipmentService;
import hk.gov.hyd.bunting.misc.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:8082", "http://10.23.106.240:8082"})
@RequestMapping("/api/equipments")
public class EquipmentController {

    final private EquipmentService equipmentService;

    @Autowired
    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GetMapping
    public List<Equipment> getAllEquipments() {
        return equipmentService.getAllEquipments();
    }

/*
    @GetMapping("/hello-world")
    public String helloWorld() {
        return "Hello World!";
    }
*/

    @GetMapping("/{id}")
    public ResponseEntity<Equipment> getEquipmentById(@PathVariable int id) {
        System.out.println("Controller: Received request for equipment with ID: " + id);
        Equipment equipment = equipmentService.getEquipmentById(id);
        System.out.println("Controller: Equipment fetched: " + equipment);
        return equipment != null ? ResponseEntity.ok(equipment) : ResponseEntity.notFound().build();
    }

    @PostMapping("/data-retrieval")
    public ResponseEntity<List<Equipment>> retrieveData(@RequestBody QueryObject queryObject) {
        try {
            List<Equipment> equipments = equipmentService.retrieveData(queryObject);
            return ResponseEntity.ok(equipments);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public Equipment createEquipment(@RequestBody Equipment equipment) {
        equipmentService.createEquipment(equipment);
        return equipment;
    }

    @PostMapping("/batch")
    public ResponseEntity<ServiceResponse> createEquipmentList(@RequestBody List<Equipment> equipmentList) {

        ServiceResponse response = equipmentService.createEquipmentList(equipmentList);
        if (response.notValidLamppostNo() || response.notValidDateRange()) {
            return ResponseEntity.status(422).body(response); // LampPost number incorrect!!
        }
        if (response.getConflictEquipments().isEmpty() && response.getConflictBuntings().isEmpty()) {
            return ResponseEntity.status(200).body(response); //OK
        } else {
            return ResponseEntity.status(409).body(response);  // Conflict status for duplicates
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipment> updateEquipment(@PathVariable int id, @RequestBody Equipment equipmentDetails) {
        equipmentService.updateEquipment(id, equipmentDetails);
        return ResponseEntity.ok(equipmentDetails);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteEquipment(@RequestParam("ids") List<Long> ids) {
        try {
            equipmentService.deleteEquipments(ids);
            return ResponseEntity.noContent().build();
            //"Resource deleted successfully"
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @GetMapping("/lamppost/{lamppostNo}")
    public ResponseEntity<List<Equipment>> getEquipmentByLamppostNo(@PathVariable String lamppostNo) {
        List<Equipment> equipments = equipmentService.getEquipmentByLamppostNo(lamppostNo);
        return !equipments.isEmpty() ? ResponseEntity.ok(equipments) : ResponseEntity.notFound().build();
    }

    @GetMapping("/lampposts")
    public ResponseEntity<List<Equipment>> getEquipmentByLamppostNoList(@RequestParam String lamppostNoList) {
        List<Equipment> equipments = equipmentService.getEquipmentByLamppostNoList(lamppostNoList);
        return !equipments.isEmpty() ? ResponseEntity.ok(equipments) : ResponseEntity.notFound().build();
    }

}
