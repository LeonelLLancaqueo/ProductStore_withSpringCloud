package com.example.CarritoDeCompras;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.CarritoDeCompras.entity.Category;
import com.example.CarritoDeCompras.entity.Product;
import com.example.CarritoDeCompras.repository.ProductRepository;
import com.example.CarritoDeCompras.service.ProductService;
import com.example.CarritoDeCompras.service.ProductServiceImpl;

@SpringBootTest
public class ProductServiceMockTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        productService = new ProductServiceImpl(productRepository);
            
        Product computer= Product.builder()
            .id(1L)
            .name("computer")
            .category(Category.builder().id(1L).build())
            .price(Double.parseDouble("12.5"))
            .stock(Double.parseDouble("5"))
            .build();
        
        Mockito.when(productRepository.getReferenceById(1L))
            .thenReturn(computer);

        Mockito.when(productRepository.save(computer))
            .thenReturn(computer);


    }
    @Test
    public void whenValidSetID_ThenReturnproduct(){
        Product found = productService.getProduct(1L);
        Assertions.assertThat(found.getName()).isEqualTo("computer");
    }

    @Test
    public void whenValididUpdateStock_ThenReturnNewStock(){
        Product newStock= productService.updateStock(1L, Double.parseDouble("8"));
        Assertions.assertThat(newStock.getStock()).isEqualTo(13);

    }


}
