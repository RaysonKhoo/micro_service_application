package org.example.Service;

import jakarta.transaction.Transactional;
import org.example.Repository.InventoryRepository;
import org.example.sharedentities.Inventory;
import org.example.sharedentities.InventoryProductDetails;
import org.example.sharedentities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private final RestTemplate restTemplate;

    public InventoryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public Inventory createInventoryProduct(Inventory inventory){
        String url = "http://localhost:8081/api/product/list/" + inventory.getProductID();
        Product product = restTemplate.getForObject(url, Product.class);
        if (product == null) {
            throw new IllegalStateException("Product with ID " + inventory.getProductID() + " does not exist");
        }

        boolean exists = inventoryRepository.existsByProductID(inventory.getProductID());
        if (exists) {
            throw new IllegalStateException("Inventory with Product ID " + inventory.getProductID() + " already exists");
        }
        inventory.setCreatedBy("admin");
        inventory.setUpdatedBy("admin");


        return inventoryRepository.save(inventory);
    }
    public InventoryProductDetails  getProductDetails(Long productId){
        Inventory inventory = inventoryRepository.findByProductID(productId)
                .orElseThrow(() -> new IllegalArgumentException("Inventory with ProductID " + productId + " not found"));
        String url = "http://localhost:8081/api/product/list/" + productId;
        Product product = restTemplate.getForObject(url, Product.class);

        if (product == null) {
            throw new IllegalArgumentException("Product with ID " + productId + " does not exist");
        }
        return new InventoryProductDetails(product, inventory.getQuantity());
    }

    @Transactional
    public Inventory updateInventoryStock(Long inventoryId, Inventory inventoryUpdate){
        Inventory existingInventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new IllegalStateException("Inventory with id" +inventoryId+ " not found"));
        if(inventoryUpdate.getQuantity() == null){
            throw new IllegalStateException("Inventory quantity cannot be null");
        }
        existingInventory.setQuantity(inventoryUpdate.getQuantity());
        if(inventoryUpdate.getQuantity() == 0){
            existingInventory.setAvailability(false);
        }
        if(inventoryUpdate.getQuantity() !=0){
            existingInventory.setAvailability(true);
        }
        existingInventory.setUpdatedBy("admin");

        return inventoryRepository.save(existingInventory);
    }

    public void deleteExistInventory (Long inventoryId){
        boolean exits = inventoryRepository.existsById(inventoryId);

        if(!exits){
            throw new IllegalStateException("Inventory with id" + inventoryId + " not found");
        }
            inventoryRepository.deleteById(inventoryId);
    }
}
