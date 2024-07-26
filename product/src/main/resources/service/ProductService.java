package org.example.service;

import jakarta.transaction.Transactional;
import org.example.Repository.InventoryRepository;
import org.example.error.ProductNotFoundException;
import org.example.repository.CartRepository;
import org.example.repository.ProductRepository;
import org.example.repository.ProductToCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private ProductToCartRepository productToCartRepository;
    @Autowired
    private  CartRepository cartRepository;
    @Autowired
    private InventoryRepository inventoryRepository;

    public ProductService(ProductRepository productRepository)
    {
        this.productRepository = productRepository;
    }
    public Product addNewProduct(Product product) {

        Optional<Product> productOptional = productRepository.findProductByName(product.getName());
        if (productOptional.isPresent()) {
            throw new IllegalStateException("Product with name '" + product.getName() + "' already exists");
        }
        if(product.getName()==null || product.getPrice()==null){
            throw new IllegalStateException("Product name and product price can not empty");
        }
        product.setCreatedBy("admin");
        product.setUpdatedBy("admin");

        return productRepository.save(product);
    }

    public List<Product> getALlProductDetails(){
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new ProductNotFoundException("Product with id '" + id + "' not found");
        }
    }
    @Transactional
    public Product updateProductDetails(Long productId, Product productUpdate) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalStateException("Product with id '" + productId + "' not found"));

        if(productUpdate.getName()==null || productUpdate.getPrice() ==null)
        {
            throw new IllegalStateException("Product name and price cannot be empty");
        }
        Optional<Product> productOptional = productRepository.findProductByName(productUpdate.getName());
        if (productOptional.isPresent()&& !productOptional.get().getProductID().equals(productId)) {
            throw new IllegalStateException("Product with name '" + productUpdate.getName() + "' already exists");
        }
        existingProduct.setName(productUpdate.getName());
        existingProduct.setDescription(productUpdate.getDescription());
        existingProduct.setPrice(productUpdate.getPrice());
        existingProduct.setUpdatedBy("admin"); // Assuming this is how updatedBy is set

        // Save the updated product back to the repository
        return productRepository.save(existingProduct);
    }

    public void deleteExistProduct(Long productId){

        boolean exits = productRepository.existsById(productId);
        if (!exits){
            throw new IllegalStateException("Product with id " + productId + " does not exist");
        }
         productRepository.deleteById(productId);
    }

    public ProductToCart addNewProduct(Long productID, Long cartID, int quantity){
        Product product = productRepository.findById(productID)
                .orElseThrow(() -> new IllegalStateException("Product with id " + productID + " does not exist"));
        Cart cart = cartRepository.findById(cartID)
                .orElseThrow(()-> new IllegalStateException("Cart with id " + cartID + " does not exits."));

        Inventory inventory = inventoryRepository.findByProductID(productID)
                .orElseThrow(()-> new IllegalStateException("Product Id " + productID + " does not found"));
        if(inventory.getQuantity() < quantity){
            throw new IllegalStateException("Insufficient inventory for product id " + productID);
        }
        inventory.setQuantity(inventory.getQuantity()- quantity);
        inventoryRepository.save(inventory);

        Optional<ProductToCart> existingProductInCart = productToCartRepository.findByProductAndCart(product,cart);
        if(existingProductInCart.isPresent()){

            ProductToCart productToCart = existingProductInCart.get();
            productToCart.setQuantity(productToCart.getQuantity()+ quantity);
            return productToCartRepository.save(productToCart);

        }else{

            if("inactive".equals(cart.getStatus())){
                throw new IllegalStateException("Cart id " + cartID + " is in status inactive");
            }
            ProductToCart productToCart = new ProductToCart();
            productToCart.setProduct(product);
            productToCart.setQuantity(quantity);
            productToCart.setCreatedBy("admin");
            productToCart.setCart(cart);
            return productToCartRepository.save(productToCart);
        }
    }

    public void removeProductFromCart( Long productId , Long cartId, int quantity){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalStateException("Product with ID " + productId + " does not exist"));
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalStateException("Cart with (ID " + cartId + " does not exist"));

        ProductToCart productToCart = productToCartRepository.findByProductAndCart(product, cart)
                .orElseThrow(()-> new IllegalStateException("Product with id " + productId + " is not in the cart" ));
        if(productToCart.getQuantity() > quantity){
            productToCart.setQuantity((productToCart.getQuantity())-quantity);
            productToCartRepository.save(productToCart);
        }else {
            productToCartRepository.delete(productToCart);
        }
        Inventory inventory = inventoryRepository.findByProductID(productId)
                .orElseThrow(()-> new IllegalStateException("Inventory with product id " + productId + " does not exits" ));
        inventory.setQuantity(inventory.getQuantity() + quantity);
        inventoryRepository.save(inventory);
    }

    @Transactional
    public List<ProductToCart> updateProductTotal(Long cartID,List<ProductCheckoutRequest> productCheckoutRequests){

        Cart cart = cartRepository.findById(cartID)
                .orElseThrow(() -> new IllegalStateException("Cart with (ID " + cartID + " does not exist"));

        if("inactive".equals(cart.getStatus())){
            throw new IllegalStateException("Cart id " + cartID + " is in status inactive");
        }
        List<ProductToCart> updatedProducts = new ArrayList<>();
        for(ProductCheckoutRequest request: productCheckoutRequests){
            Long productID = request.getProductId();
            int checkoutQuantity = request.getQuantity();

            ProductToCart productToCart = productToCartRepository.findByProduct_ProductIDAndCart_CartID(productID,cartID)
                    .orElseThrow(()-> new IllegalStateException("Product with id " + productID + "not found in cart " + cartID));

            if(productToCart.getQuantity() < checkoutQuantity){
                throw new IllegalStateException("Insufficient inventory for product id " + productID);
            }
            productToCart.setQuantity(productToCart.getQuantity()-checkoutQuantity);
            if(productToCart.getQuantity() <= 0){
                productToCartRepository.delete(productToCart);
            }else{
                updatedProducts.add(productToCartRepository.save(productToCart));
            }

        }
        return updatedProducts;
    }
}
