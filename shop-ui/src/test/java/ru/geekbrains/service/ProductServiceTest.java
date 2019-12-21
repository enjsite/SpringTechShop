package ru.geekbrains.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.geekbrains.controllers.repr.ProductRepr;
import ru.geekbrains.persist.model.Brand;
import ru.geekbrains.persist.model.Category;
import ru.geekbrains.persist.model.Product;
import ru.geekbrains.persist.repo.ProductRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductServiceTest {

    private ProductService productService;

    private ProductRepository productRepository;

    @BeforeEach
    public void init() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductServiceImpl();
        ((ProductServiceImpl) productService).setProductRepository(productRepository);
    }

    @Test
    public void findAllAndSplitProductsByTest() {
        when(productRepository.findAll()).thenReturn(new ArrayList<Product>() {{
            for (long i=0; i<10; i++) {
                Product product = new Product();
                product.setBrand(new Brand());
                product.setCategory(new Category());
                product.setPictures(Collections.emptyList());
                product.setName("Product");
                product.setId(i);
                add(product);
            }
        }});
        List<List<ProductRepr>> result = productService.findAllAndSplitProductsBy(4);
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(4, result.get(0).size());
        assertEquals(4, result.get(1).size());
        assertEquals(2, result.get(2).size());
    }

    @Test
    public void findAll() {
        when(productRepository.findAll()).thenReturn(new ArrayList<Product>() {{
            for (long i=0; i<5; i++) {
                Product product = new Product();
                product.setBrand(new Brand());
                product.setCategory(new Category());
                product.setPictures(Collections.emptyList());
                product.setName("Product"+i);
                product.setId(i);
                add(product);
            }
        }});
        List<ProductRepr> result = productService.findAll();
        assertNotNull(result);
        assertEquals(5, result.size());
    }
}
