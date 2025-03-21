package hk.gov.hyd.bunting.controller;

import hk.gov.hyd.bunting.misc.QueryObject;
import hk.gov.hyd.bunting.model.Bunting;
import hk.gov.hyd.bunting.service.BuntingService;
import hk.gov.hyd.bunting.misc.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:8082", "http://10.23.106.240:8082"})
@RequestMapping("/api/buntings")
public class BuntingController {

    final private BuntingService buntingService;

    @Autowired
    public BuntingController(BuntingService buntingService) {
        this.buntingService = buntingService;
    }

    @GetMapping
    public List<Bunting> getAllBuntings() {
        return buntingService.getAllBuntings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bunting> getBuntingById(@PathVariable long id) {
        Bunting bunting = buntingService.getBuntingById(id);
        return bunting != null ? ResponseEntity.ok(bunting) : ResponseEntity.notFound().build();
    }

    @GetMapping("/calendar")
    public ResponseEntity<List<Bunting>> getBuntingsByFilter(
            @RequestParam(required = false) String lamppostNo,
            @RequestParam(required = false) String event,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<Bunting> buntings;
        if (!lamppostNo.isEmpty()) {
            buntings = buntingService.getBuntingByLamppostNoAndDateRange(lamppostNo, startDate, endDate);
        } else if (!event.isEmpty()) {
            buntings = buntingService.getBuntingByEventAndDateRange(event, startDate, endDate);
        } else {
            buntings = buntingService.getBuntingByDateRange(startDate, endDate);
        }
        return ResponseEntity.ok(buntings);
    }




    @PostMapping("/data-retrieval")
    public ResponseEntity<List<Bunting>> retrieveData(@RequestBody QueryObject queryObject) {
        try {
            List<Bunting> buntings = buntingService.retrieveData(queryObject);
            return ResponseEntity.ok(buntings);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public Bunting createBunting(@RequestBody Bunting bunting) {
        buntingService.createBunting(bunting);
        return bunting;
    }

    @PostMapping("/batch")
    public ResponseEntity<ServiceResponse> createBuntingList(@RequestBody List<Bunting> buntingList) {
        ServiceResponse response = buntingService.createBuntingList(buntingList);
        if (response.notValidLamppostNo() || response.notValidDateRange()) {
            return ResponseEntity.status(422).body(response);
        }
        if (response.getConflictEquipments().isEmpty() && response.getConflictBuntings().isEmpty()) {
            return ResponseEntity.status(200).body(response); //OK
        } else {
            return ResponseEntity.status(409).body(response);  // Conflict status for duplicates
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Bunting> updateBunting(@PathVariable long id, @RequestBody Bunting buntingDetails) {
        buntingService.updateBunting(id, buntingDetails);
        return ResponseEntity.ok(buntingDetails);
    }
/*
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteBuntings(@RequestBody List<Long> ids) {
        buntingService.deleteBuntings(ids);
        return ResponseEntity.noContent().build();
    }
*/
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteBunting(@RequestParam("ids") List<Long> ids) {
        try {
            buntingService.deleteBuntings(ids);
            return ResponseEntity.noContent().build();
            //"Resource deleted successfully"
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
}



