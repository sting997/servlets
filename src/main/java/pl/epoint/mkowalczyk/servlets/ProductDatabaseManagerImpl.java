package pl.epoint.mkowalczyk.servlets;

import java.util.List;

public class ProductDatabaseManagerImpl implements ProductManager {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL="jdbc:postgresql://localhost/products";

    //  Database credentials
    static final String USER = "products";
    static final String PASS = "password";

    private void getDirectConnection(){

    }

    @Override
    public List<Product> getProductsList() {
        return null;
    }

    @Override
    public Product getProductByPK(Integer PK) {
        return null;
    }

    @Override
    public void insertProduct(Product product) {

    }

    @Override
    public void updateProduct(Product product) {

    }

    @Override
    public void deleteProduct(Product product) {

    }
}
