package org.example.sharedentities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="ProductToCart")
public class ProductToCart {

    @Id
    @SequenceGenerator(
            name = "product_cart_sequence",
            sequenceName = "product_cart_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_cart_sequence"
    )

    private  Long productCartID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cartID", referencedColumnName = "cartID")
    @JsonBackReference("cart-ProductToCart")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="productID", referencedColumnName = "productID")
    @JsonBackReference("product-ProductToCart")
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

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


    @Override
    public String toString() {
        return "ProductToCart [id=" + productCartID + ", cart=" + cart + ", product=" + product + ", quantity=" + quantity +"]";
    }
}
