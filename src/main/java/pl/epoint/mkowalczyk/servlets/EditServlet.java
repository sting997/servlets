package pl.epoint.mkowalczyk.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Serwlet obsługujący edycję oraz dodawanie nowych produktów.
 *
 * @author Michał Kowalczyk
 */
@WebServlet("/edit/*")
class EditServlet extends HttpServlet {
    private ProductManager productManager = ProductMemoryManagerImpl.INSTANCE;

    //TODO refactor
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer productID = getProductIdFromRequest(req);
        req.setAttribute("productId", productID);
        req.getRequestDispatcher("/WEB-INF/edit.jsp").forward(req, resp);
    }

    private Integer getProductIdFromRequest(HttpServletRequest req) {
        String requestProductId = req.getParameter("productId");
        if (requestProductId != null)
            return Integer.valueOf(requestProductId);
        else
            return Integer.valueOf(0);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = getActionFromRequest(req, "action");
        if (isSaveProductAction(action)) {
            processSaveAction(req, resp);
        }
    }

    private void processSaveAction(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (isNewProduct(req))
            addNewProduct(req);
        else
            editProduct(req);
        redirectToProductList(resp);
    }

    private void redirectToProductList(HttpServletResponse resp) throws IOException {
        resp.sendRedirect("list");
    }

    private void addNewProduct(HttpServletRequest req) {
        String name = req.getParameter("product_name");
        BigDecimal price = new BigDecimal(req.getParameter("product_price"));
        Product newProduct = new Product(name, price);
        productManager.insertProduct(newProduct);
    }

    private void editProduct(HttpServletRequest req) {
        String requestProductId = req.getParameter("productId");
        Integer productId = Integer.valueOf(requestProductId);
        String name = req.getParameter("product_name");
        BigDecimal price = new BigDecimal(req.getParameter("product_price"));
        Product product = new Product(productId, name, price);
        productManager.updateProduct(product);
    }

    private String getActionFromRequest(HttpServletRequest req, String action) {
        return req.getParameter(action);
    }

    private boolean isSaveProductAction(String action) {
        return action != null && action.equals("save_product");
    }

    private boolean isNewProduct(HttpServletRequest req) {
        String requestProductId = req.getParameter("productId");
        return requestProductId == null || Integer.valueOf(requestProductId).equals(0);
    }
}
