package com.example.ecommerce.controller;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCartByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartByUser(userId));
    }

    @PostMapping("/{userId}/add")
    public ResponseEntity<Cart> addToCart(@PathVariable Long userId, @RequestBody Product product) {
        return ResponseEntity.ok(cartService.addToCart(userId, product));
    }

    @PostMapping("/{userId}/remove")
    public ResponseEntity<Cart> removeFromCart(@PathVariable Long userId, @RequestBody Product product) {
        return ResponseEntity.ok(cartService.removeFromCart(userId, product));
    }
}