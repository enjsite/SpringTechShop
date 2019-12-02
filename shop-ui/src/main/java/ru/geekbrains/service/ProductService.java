package ru.geekbrains.service;

import ru.geekbrains.controllers.repr.ProductRepr;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<ProductRepr> findById(Long id);

    List<ProductRepr> findAll();

    List<List<ProductRepr>> findAllAndSplitProductsBy(int groupSize);
}
