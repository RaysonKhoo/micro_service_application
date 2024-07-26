package org.example.controller;

import org.example.service.CartService;
import org.example.sharedentities.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/cart")
public class CartController {
    @Autowired
    private final CartService cartService;
    public CartController(CartService cartService){
        this.cartService =cartService;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<Object> createCart(@RequestBody Cart cart){
        try {
            Cart SavedCart = cartService.createEmptyCart(cart);
            return new ResponseEntity<>(SavedCart, HttpStatus.CREATED);
        }catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/list")
    public  ResponseEntity<List<Cart>>  getCart(){
        return new ResponseEntity<>(cartService.getCartDetails(), HttpStatus.OK);
    }

    @PutMapping(path = "/update/{cartId}")
    public ResponseEntity<Object> updateCartStatus(@PathVariable ("cartId") Long cartId, @RequestBody Cart cartUpdate)
    {
        try{
            return  new ResponseEntity<>(cartService.updateCartDetails(cartId, cartUpdate), HttpStatus.OK);
        }
        catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/delete/{cartId}")
    public ResponseEntity<Object> deleteCart(@PathVariable("cartId") Long cartId){
        try{
            String message = "Product with id " + cartId + " was deleted";
            cartService.deleteExitsCart(cartId);
            return new ResponseEntity<>(message,HttpStatus.OK);
        }catch (IllegalStateException e){
          return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
   }
    }
}
