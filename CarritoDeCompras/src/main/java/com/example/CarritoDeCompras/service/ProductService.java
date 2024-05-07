package com.example.CarritoDeCompras.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.CarritoDeCompras.entity.Category;
import com.example.CarritoDeCompras.entity.Product;



@Service
public interface ProductService {
    public List<Product> listAllProducts();
    public Product getProduct(Long id);
    public Product createProduct(Product product);
    public Product updateProduct(Product product); 
    public Product deleteProduct(Long id);
    public List<Product> findByCategory(Category category);
    public Product updateStock(Long id, Double quantity);
    
}
