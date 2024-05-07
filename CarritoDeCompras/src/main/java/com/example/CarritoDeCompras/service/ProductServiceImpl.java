package com.example.CarritoDeCompras.service;

import java.util.Date;
import java.util.List;


import org.springframework.stereotype.Service;

import com.example.CarritoDeCompras.entity.Category;
import com.example.CarritoDeCompras.entity.Product;
import com.example.CarritoDeCompras.repository.ProductRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{

    
    private ProductRepository productRepository;

    @Override
    public List<Product> listAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);

    }

    @Override
    public Product createProduct(Product product) {
        product.setStatus("CREATED");
        product.setCreateAt(new Date());

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Product productDB = getProduct(product.getId());
        if(null== productDB){
            return null;
        }else{
            productDB.setName(product.getName());
            productDB.setDescription(product.getDescription());
            productDB.setCategory(product.getCategory());
            productDB.setPrice(product.getPrice());
            return productRepository.save(productDB);
        }
    }

    @Override
    public Product deleteProduct(Long id) {
        Product productDB = getProduct(id);
        if(null== productDB){
            return null;
        }else{
            productDB.setStatus("DELETED");
            return productRepository.save(productDB);
        }
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public Product updateStock(Long id, Double quantity) {
        Product productDB = getProduct(id);
        if(null== productDB){
            return null;
        }else{
            productDB.setStock(productDB.getStock()+ quantity);
            return productRepository.save(productDB);
        }
    }
    
}
