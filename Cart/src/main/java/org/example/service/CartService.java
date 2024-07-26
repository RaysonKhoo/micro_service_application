package org.example.service;

import jakarta.transaction.Transactional;
import org.example.repository.CartRepository;
import org.example.sharedentities.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    public final CartRepository cartRepository;
    @Autowired
    public CartService(CartRepository cartRepository)
    {
        this.cartRepository  = cartRepository;
    }

    public Cart createEmptyCart(Cart cart) {

    if(cart.getStatus() ==null){
        throw new IllegalStateException("Cart status cannot be null");
    }
        cart.setCreatedBy("admin");
        cart.setUpdatedBy("admin");

        return cartRepository.save(cart);
    }

    public List<Cart>getCartDetails(){
        return cartRepository.findAll();
    }

    @Transactional
    public Cart updateCartDetails(Long cartId, Cart cartUpdate){

        Cart existingCart = cartRepository.findById(cartId).orElseThrow(()-> new IllegalStateException("Cart with id " +cartId+ "not found"));
        if(cartUpdate.getStatus()==null){
            throw new IllegalStateException("Product Status can not be null");
        }else {
            existingCart.setStatus(cartUpdate.getStatus());
        }
        return cartRepository.save(existingCart);
    }

    public void  deleteExitsCart(Long cartId){

        boolean exits = cartRepository.existsById(cartId);
        if (!exits){
            throw new IllegalStateException("Cart with id " + cartId + "does not exist");
        }
         cartRepository.deleteById(cartId);
    }

}
