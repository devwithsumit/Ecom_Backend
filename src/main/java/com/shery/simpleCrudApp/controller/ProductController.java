package com.shery.simpleCrudApp.controller;


import com.shery.simpleCrudApp.model.Product;
import com.shery.simpleCrudApp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService service;


    @GetMapping("/products")
    public ResponseEntity<?> getProduct() {
        try {
            return new ResponseEntity<>(service.getProducts(), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //get products by id
    @GetMapping("/products/{prodId}")
    public ResponseEntity<Product> getProductById(@PathVariable int prodId) {

        Product product = service.getProductById(prodId);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //get productImage by id
    @GetMapping("/products/{prodId}/image")
    public ResponseEntity<?> getProdImageById(@PathVariable int prodId) {
        Product product = service.getProductById(prodId);
        byte[] imageFile = product.getImageData();

        return ResponseEntity.ok()
                .body(imageFile);
    }

    //Search controller
    @GetMapping("/products/search")
    public ResponseEntity<?> searchProducts(@RequestParam String keyword) {
        try {
            List<Product> products = service.searchProducts(keyword.toLowerCase());
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Add product controller
    @PostMapping("/products")
    public ResponseEntity<?> addProduct(
            @RequestPart Product product,
            @RequestPart MultipartFile imageFile
    ) {
        try {
            Product prod = service.addProduct(product, imageFile);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update product controller
    @PutMapping("/products/{prodId}")
    public ResponseEntity<?> updateProduct(
            @PathVariable int prodId,
            @RequestPart Product product,
            @RequestPart MultipartFile imageFile
    ) {

        Product product1 = null;
        try {
            product1 = service.updateProduct(product, imageFile);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to delete product", HttpStatus.BAD_REQUEST);
        }
        if(product1 != null){
            return new ResponseEntity<> ("Updated", HttpStatus.OK);
        }else{
            return new ResponseEntity<> ("Failed to update", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/products/{prodId}")
    public ResponseEntity<?> deleteProduct(@PathVariable int prodId) {
        Product product = service.getProductById(prodId);
        if (product != null) {
            service.deleteProduct(prodId);
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to delete the product", HttpStatus.CONFLICT);
        }
    }

}
