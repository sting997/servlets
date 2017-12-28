package pl.epoint.mkowalczyk.servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Serwlet obsługujący edycję oraz dodawanie nowych produktów.
 *
 * @author Michał Kowalczyk
 */
@WebServlet("/edit/*")
class EditServlet extends HttpServlet {
    private final ProductManager productManager = new ProductDatabaseManagerImpl();
    private int privateFieldCounter;
    private final static String EDIT_JSP_PATH = "/WEB-INF/edit.jsp";

    @Override
    public void init() {
        privateFieldCounter = 0;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        incrementCounters(req);
        setRequestAttributes(req);
        req.getRequestDispatcher(EDIT_JSP_PATH).forward(req, resp);
    }

    private void incrementCounters(HttpServletRequest req) {
        incrementPrivateFieldCounter();
        incrementServletContextCounter(req);
        incrementSessionCounter(req);
    }

    private void incrementSessionCounter(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Integer servletSessionCounter = (Integer) session.getAttribute("servletSessionCounter");
        if (servletSessionCounter == null)
            servletSessionCounter = Integer.valueOf(1);
        else
            servletSessionCounter = Integer.valueOf(servletSessionCounter.intValue() + 1);
        session.setAttribute("servletSessionCounter", servletSessionCounter);
    }

    private void incrementServletContextCounter(HttpServletRequest req) {
        ServletContext servletContext = req.getServletContext();
        Integer servletContextCounter = (Integer) servletContext.getAttribute("servletContextCounter");
        if (servletContextCounter == null)
            servletContextCounter = Integer.valueOf(1);
        else
            servletContextCounter = Integer.valueOf(servletContextCounter.intValue() + 1);
        servletContext.setAttribute("servletContextCounter", servletContextCounter);
    }

    private void incrementPrivateFieldCounter() {
        privateFieldCounter++;
    }

    private void setRequestAttributes(HttpServletRequest req) {
        Integer productID = getProductIdFromRequest(req);
        if (productID != null)
            req.setAttribute("productId", productID);
        req.setAttribute("privateFieldCounter", privateFieldCounter);
    }

    private Integer getProductIdFromRequest(HttpServletRequest req) {
        String requestProductId = req.getParameter("productId");
        if (requestProductId != null)
            return Integer.valueOf(requestProductId);
        else
            return null;
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
        return requestProductId == null;
    }
}
