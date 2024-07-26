package org.example.repository;

import org.example.sharedentities.Cart;
import org.example.sharedentities.Product;
import org.example.sharedentities.ProductToCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductToCartRepository extends JpaRepository<ProductToCart, Long> {
    Optional<ProductToCart> findByProductAndCart(Product product, Cart cart);
    Optional<ProductToCart> findByProduct_ProductIDAndCart_CartID(Long productID, Long cartID);
//    @Query("SELECT pc.cart.cartID, COUNT(pc.product.productID) FROM ProductToCart pc GROUP BY pc.cart.cartID")
}
