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

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@AllArgsConstructor
@Table(name= "Cart")
@Getter
@Setter
@NoArgsConstructor

public class Cart {

    @Id
    @SequenceGenerator(
            name= "cart_sequence",
            sequenceName = "cart_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "cart_sequence"
    )
    private  Long cartID;
    @Column(nullable = false)
    private String status;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("cart-ProductToCart")
    private Set<ProductToCart> productToCart = new HashSet<>();

    @CreatedBy
    private String createdBy;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdOn;

    @LastModifiedBy
    private String updatedBy;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedOn;

}
