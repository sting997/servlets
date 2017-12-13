package pl.epoint.mkowalczyk.servlets;

import java.util.List;

interface ProductManager {
    List<Product> getProductsList();

    Product getProductByPK(Integer PK);

    void insertProduct(Product product);

    void updateProduct(Product product);

    void deleteProduct(Product product);
}
