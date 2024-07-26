package org.example.sharedentities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Inventory")
@Setter
@Entity
public class Inventory {

    @Id
    @SequenceGenerator(
            name = "inventory_sequence",
            sequenceName = "inventory_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "inventory_sequence"
    )

    private Long inventoryID;
    private Long productID;
    @Column(nullable = false)
    private Integer quantity;

    @CreatedBy
    private String createdBy;
    private boolean availability;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdOn;

    @LastModifiedBy
    private String updatedBy;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedOn;
}
