package com.example.CarritoDeCompras.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.CarritoDeCompras.entity.Category;
import com.example.CarritoDeCompras.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

    public List<Product> findByCategory(Category category);

    

}

