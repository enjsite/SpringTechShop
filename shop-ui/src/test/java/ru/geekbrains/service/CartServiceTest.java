package ru.geekbrains.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.geekbrains.controllers.repr.ProductRepr;
import ru.geekbrains.service.model.LineItem;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CartServiceTest {

    private CartService cartService;

    @BeforeEach
    public void init() {
        cartService = new CartServiceImpl();
    }

    @Test
    public void addProductQtyTest() {
        ProductRepr repr = new ProductRepr();
        repr.setId(1L);
        repr.setName("Product");
        repr.setPrice(new BigDecimal(1234));
        cartService.addProductQty(repr, "Some Color", "Some material", 5);

        List<LineItem> lineItems = cartService.getLineItems();
        assertNotNull(lineItems);
        assertEquals(1, lineItems.size());
        assertEquals(1L, lineItems.get(0).getProductId());
        assertEquals("Product", lineItems.get(0).getProductRepr().getName());
    }

    @Test
    public void removeProductQtyTest() {
        ProductRepr repr = new ProductRepr();
        repr.setId(1L);
        repr.setName("Product");
        repr.setPrice(new BigDecimal(1234));
        cartService.addProductQty(repr, "Some Color", "Some material", 5);

        cartService.removeProductQty(repr, "Some Color", "Some material", 3);

        List<LineItem> lineItems = cartService.getLineItems();
        assertNotNull(lineItems);
        assertEquals(1, lineItems.size());
        assertEquals(2, lineItems.get(0).getQty());
    }
}