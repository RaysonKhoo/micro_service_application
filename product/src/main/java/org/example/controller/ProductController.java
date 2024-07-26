package org.example.controller;

import org.example.service.ProductService;
import org.example.sharedentities.Product;
import org.example.sharedentities.ProductCheckoutRequest;
import org.example.sharedentities.ProductToCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/product")
public class ProductController {
    @Autowired
    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<Object> addProduct(@RequestBody Product product) {
        try {
            Product savedProduct = productService.addNewProduct(product);
            return new ResponseEntity<>(savedProduct,HttpStatus.CREATED);
        }catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/list")
    public ResponseEntity<List<Product>> getALlProductDetails(){
        return new ResponseEntity<>(productService.getALlProductDetails(), HttpStatus.OK);
    }

    @GetMapping(path = "/list/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
        }catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/update/{productId}")
    public ResponseEntity<Object> updateProduct(@PathVariable ("productId") Long productId,@RequestBody Product productUpdate) {
        try {
            return new ResponseEntity<>(productService.updateProductDetails(productId, productUpdate),HttpStatus.OK);
        }catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/delete/{productId}")
    public ResponseEntity<Object> deleteProduct(@PathVariable ("productId") Long productId) {
        try {
            String message = "Product with id " + productId + " was deleted";
            productService.deleteExistProduct(productId);
            return new ResponseEntity<>(message,HttpStatus.OK );
        }catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/addCart/{productID}/{cartID}")
    public ResponseEntity<Object> addToCart(@PathVariable Long productID , @PathVariable Long cartID, @RequestBody ProductToCart addCart){
        try {
            int quantity = addCart.getQuantity();
            ProductToCart savedProduct = productService.addNewProduct(productID,cartID,quantity);
            return new ResponseEntity<>(savedProduct,HttpStatus.CREATED);
        }catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping(path = "/delete/{productId}/{cartId}")
    public ResponseEntity<Object> removeFromCart(@PathVariable ("productId") Long productId, @PathVariable ("cartId") Long cartId, @RequestBody ProductToCart removeProduct) {
        try {
            int quantity = removeProduct.getQuantity();
            String message = "Product with id " + productId + " was deleted";
            productService.removeProductFromCart(productId, cartId, quantity);
            return new ResponseEntity<>(message,HttpStatus.OK );
        }catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path="/checkout/{cartId}")
    public ResponseEntity<Object> updateProductQuantity (@PathVariable ("cartId") Long cartID, @RequestBody List<ProductCheckoutRequest> productCheckoutRequests){

        try{
            List<ProductToCart> updateStock = productService.updateProductTotal(cartID, productCheckoutRequests);
            return new ResponseEntity<>(updateStock,HttpStatus.OK);
        }catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


}
