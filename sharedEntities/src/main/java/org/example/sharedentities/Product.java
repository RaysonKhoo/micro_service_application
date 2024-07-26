package org.example.sharedentities;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@Table(name= "Product")
@Getter
@Setter
@NoArgsConstructor

public class Product {

    @Id
    @SequenceGenerator(
            name= "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )


    private  Long productID;
    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("product-ProductToCart")
    private Set<ProductToCart> productToCart  = new HashSet<>();

    @CreatedBy
    private String createdBy;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdOn;

    @LastModifiedBy
    private String updatedBy;

    @UpdateTimestamp
    private LocalDateTime updatedOn;

}
