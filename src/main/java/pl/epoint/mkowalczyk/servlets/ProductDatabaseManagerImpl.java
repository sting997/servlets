package pl.epoint.mkowalczyk.servlets;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Wersja ProductManger'a przechowująca dane produktów w bazie danych.
 *
 * @author Michał Kowalczyk
 */
public class ProductDatabaseManagerImpl implements ProductManager {

    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://localhost/products";

    //  Database credentials
    private static final String USER = "products";
    private static final String PASS = "password";

    private boolean isDataSourceEnabled = true;

    private Connection getConnection() throws ClassNotFoundException, SQLException,
            InstantiationException, IllegalAccessException, NamingException {
        if (isDataSourceEnabled)
            return getDataSourceConnection();
        else
            return getDirectConnection();
    }

    private Connection getDirectConnection() throws ClassNotFoundException, IllegalAccessException,
            InstantiationException, SQLException {
        Class.forName(JDBC_DRIVER).newInstance();
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    private Connection getDataSourceConnection() throws SQLException,NamingException {
        InitialContext initialContext = new InitialContext();
        DataSource dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/products");
        return dataSource.getConnection();
    }

    @Override
    public List<Product> getProductsList() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                Integer PK = resultSet.getInt("id");
                String name = resultSet.getString("name");
                BigDecimal price = resultSet.getBigDecimal("price");
                Product product = new Product(PK, name, price);
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public Product getProductByPK(Integer PK) {
        String query = "SELECT * FROM products WHERE id = " + PK.intValue() + ";";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            resultSet.next();
            String name = resultSet.getString("name");
            BigDecimal price = resultSet.getBigDecimal("price");
            Product product = new Product(PK, name, price);

            return product;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insertProduct(Product product) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()
        ) {
            String query = "INSERT INTO products (name, price) " +
                    "values ('" + product.getName() + "'," + product.getPrice() + ");";
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateProduct(Product product) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()
        ) {
            String query = "UPDATE products SET " +
                    "name = '" + product.getName() + "', " +
                    "price = " + product.getPrice() +
                    "WHERE id = " + product.getPK() + ";";
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteProduct(Product product) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()
        ) {
            String query = "DELETE FROM products WHERE id = " + product.getPK() + ";";
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
