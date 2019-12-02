package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import ru.geekbrains.service.model.LineItem;
import ru.geekbrains.controllers.repr.ProductRepr;

import java.math.BigDecimal;
import java.util.*;

@Service
@Scope(scopeName = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartServiceImpl implements CartService {

    private final ProductService productService;

    private Map<LineItem, Integer> lineItems;

    @Autowired
    public CartServiceImpl(ProductService productService) {
        this.productService = productService;
        this.lineItems = new HashMap<>();
    }

    @Override
    public void addProductQty(ProductRepr productRepr, String color, String material, int qty) {
        LineItem lineItem = new LineItem(productRepr, color, material);
        lineItems.put(lineItem, lineItems.getOrDefault(lineItem, 0) + qty);
    }

    @Override
    public void removeProductQty(ProductRepr productRepr, String color, String material, int qty) {
        LineItem lineItem = new LineItem(productRepr, color, material);
        int currentQty = lineItems.getOrDefault(lineItem, 0);
        if (currentQty - qty > 0) {
            lineItems.put(lineItem, currentQty - qty);
        } else {
            lineItems.remove(lineItem);
        }
    }

    @Override
    public void removeProduct(ProductRepr productRepr, String color, String material) {
        lineItems.remove(new LineItem(productRepr, color, material));
    }

    @Override
    public List<LineItem> getLineItems() {
        lineItems.forEach(LineItem::setQty);
        return new ArrayList<>(lineItems.keySet());
    }

    @Override
    public BigDecimal getSubTotal() {
        lineItems.forEach(LineItem::setQty);
        return lineItems.keySet().stream()
                .map(LineItem::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public void updateCart(LineItem lineItem) {
        if (lineItem.getProductRepr() == null) {
            lineItem.setProductRepr(productService.findById(lineItem.getProductId())
                    .orElseThrow(IllegalArgumentException::new));
        }

        lineItems.put(lineItem, lineItem.getQty());
    }
}
