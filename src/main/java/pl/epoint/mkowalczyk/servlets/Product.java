package pl.epoint.mkowalczyk.servlets;

import java.math.BigDecimal;

/**
 * Klasa reprezentująca pojedynczy produkt.
 *
 * @author Michał Kowalczyk
 */
public class Product {
    private Integer PK;
    private String name;
    private BigDecimal price;
    private static int nextId = 1;

    Product(Integer PK, String name, BigDecimal price) {
        this.PK = PK;
        this.name = name;
        this.price = price;
    }

    Product(String name, BigDecimal price) {
        this.PK = Integer.valueOf(nextId);
        nextId++;
        this.name = name;
        this.price = price;
    }

    public Integer getPK() {
        return PK;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
