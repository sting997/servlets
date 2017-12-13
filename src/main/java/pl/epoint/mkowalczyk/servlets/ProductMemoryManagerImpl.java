package pl.epoint.mkowalczyk.servlets;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Wersja ProductManger'a przechowująca dane produktów w pamięci, zaimplementowana jako singleton.
 *
 * @author Michał Kowalczyk
 */
enum ProductMemoryManagerImpl implements ProductManager {
    INSTANCE;

    private List<Product> productsList = new CopyOnWriteArrayList<>();

    ProductMemoryManagerImpl() {
        populateDatabase(5);
    }

    private void populateDatabase(int size) {
        for (int i = 1; i <= size; i++) {
            Product product = new Product("Produkt " + i, new BigDecimal(5 * i + 13));
            productsList.add(product);
        }
    }

    @Override
    public List<Product> getProductsList() {
        return productsList;
    }

    @Override
    public Product getProductByPK(Integer PK) {
        for (Product product : productsList) {
            if (product.getPK().equals(PK))
                return product;
        }
        return null;
    }

    @Override
    public void insertProduct(Product product) {
        productsList.add(product);
    }

    @Override
    public void updateProduct(Product product) {
        int position = getProductPositionInProductsList(product);
        productsList.set(position, product);
    }

    private int getProductPositionInProductsList(Product product) {
        for (int i = 0; i < productsList.size(); i++) {
            Product currentProduct = productsList.get(i);
            if (currentProduct.getPK().equals(product.getPK()))
                return i;
        }
        throw new NoSuchElementException();
    }

    @Override
    public void deleteProduct(Product product) {
        productsList.remove(product);
    }
}
