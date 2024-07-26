package org.example.Repository;

import org.example.sharedentities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    boolean existsByProductID(Long productID);
    Optional<Inventory> findByProductID(Long productId);
}
