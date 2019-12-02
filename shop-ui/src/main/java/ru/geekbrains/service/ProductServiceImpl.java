package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.controllers.repr.ProductRepr;
import ru.geekbrains.persist.model.Product;
import ru.geekbrains.persist.repo.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<ProductRepr> findById(Long id) {
        return productRepository.findById(id).map(ProductRepr::new);
    }

    public List<List<ProductRepr>> findAllAndSplitProductsBy(int groupSize) {
        List<List<ProductRepr>> result = new ArrayList<>();
        List<ProductRepr> subList = new ArrayList<>();
        for (Product product : productRepository.findAll()) {
            subList.add(new ProductRepr(product));
            if (subList.size() == groupSize) {
                result.add(subList);
                subList = new ArrayList<>();
            }
        }
        if (!subList.isEmpty()) {
            result.add(subList);
        }
        return result;
    }
}
