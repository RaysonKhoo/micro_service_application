package org.example.Controller;

import org.example.Service.InventoryService;
import org.example.sharedentities.Inventory;
import org.example.sharedentities.InventoryProductDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private final InventoryService inventoryService;
    public InventoryController(InventoryService inventoryService){
        this.inventoryService =inventoryService;
    }

    @PostMapping(path="/create")
    public ResponseEntity<Object> createInventory(@RequestBody Inventory inventory){
        try{
            Inventory createdInventory = inventoryService.createInventoryProduct(inventory);
            return new ResponseEntity<>(createdInventory, HttpStatus.CREATED);
        }catch(IllegalStateException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
    }

    @GetMapping(path = "/list/{productId}")
    public  ResponseEntity<Object>getProductStock(@PathVariable Long productId){
        try{
            InventoryProductDetails details = inventoryService.getProductDetails(productId);
            return ResponseEntity.ok(details);
        }catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/update/{inventoryId}")
    public ResponseEntity<Object>updateInventoryQuantity (@PathVariable ("inventoryId") Long inventoryId, @RequestBody Inventory inventoryUpdate){
        try{
            return new ResponseEntity<>(inventoryService.updateInventoryStock(inventoryId, inventoryUpdate ),HttpStatus.OK);
        }catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path="/delete/{inventoryId}")
    public ResponseEntity<Object> deleteInventory(@PathVariable ("inventoryId") Long inventoryId){
        try{
            String message = "Inventory with id " + inventoryId + " was deleted";
            inventoryService.deleteExistInventory(inventoryId);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }catch (IllegalStateException e){
            return  new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    }

