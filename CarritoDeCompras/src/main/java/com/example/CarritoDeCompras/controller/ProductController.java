package com.example.CarritoDeCompras.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.ArrayList;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.CarritoDeCompras.entity.Category;
import com.example.CarritoDeCompras.entity.Product;
import com.example.CarritoDeCompras.service.ProductService;


import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    


    @GetMapping
    public ResponseEntity<List<Product>> listAllProduct(){
        List<Product> list= productService.listAllProducts();
        if(list.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(list);
        } 
         
    }
    @GetMapping("/category")
    public ResponseEntity<List<Product>> listProductByCategory(@RequestParam  Long categoryId){
        List<Product> list= productService.findByCategory(Category.builder().id(categoryId).build());
        if(list.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(list);
        } 
         
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable ("id") Long id){
        Product product= productService.getProduct(id);
        if(product == null){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(product);
        } 
         
    }
    
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody @Valid Product product) {
                
        Product product2= productService.createProduct(product);
        return  ResponseEntity.status(HttpStatus.CREATED).body(product2);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable ("id") Long id, @RequestBody Product product) {
        product.setId(id);
        Product productDB= productService.updateProduct(product);
        if(productDB == null){
            return ResponseEntity.notFound().build();
        }

        return  ResponseEntity.ok(productDB);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable ("id")Long id){
        Product productDelete= productService.deleteProduct(id);
        if(productDelete != null){
            return ResponseEntity.ok(productDelete);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
  
    @GetMapping("/{id}/stock")
    public ResponseEntity<Product> updateStockProduct(@PathVariable Long id, @RequestParam(name = "quantity", required = true) Double quantity){
        Product product= productService.updateStock(id, quantity);
        if(product != null){
            return ResponseEntity.ok(product);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseStatusException formatMessage(MethodArgumentNotValidException ex){


        List<Map<String, String>> errors= new ArrayList<Map<String,String>>();
        errors.add(Map.of("error code: ", "01"));
        ex.getBindingResult().getAllErrors().forEach(
            err -> {
                HashMap<String, String>error= new HashMap<>();
                error.put(((FieldError)err).getField(), err.getDefaultMessage());
                errors.add(error);
            }
        );


        return new ResponseStatusException(HttpStatus.BAD_REQUEST, errors.toString());
    }

}
