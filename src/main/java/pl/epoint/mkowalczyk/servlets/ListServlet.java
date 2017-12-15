package pl.epoint.mkowalczyk.servlets;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Prosty serwlet odpowiedzialny za wyświetlanie listy dostępnych produktów.
 *
 * @author Michał Kowalczyk
 */
@WebServlet("/list")
class ListServlet extends HttpServlet {

    private ProductManager productManager = ProductMemoryManagerImpl.INSTANCE;
    private int privateFieldCounter;
    private String LIST_JSP_PATH = "/WEB-INF/list.jsp";

    @Override
    public void init() {
        privateFieldCounter = 0;
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        incrementCounters(req);
        setRequestAttributes(req);
        req.getRequestDispatcher(LIST_JSP_PATH).forward(req, resp);
    }

    private void setRequestAttributes(HttpServletRequest req) {
        List<Product> products = productManager.getProductsList();
        req.setAttribute("products", products);
        req.setAttribute("privateFieldCounter", privateFieldCounter);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer productId = Integer.valueOf(req.getParameter("productId"));
        productManager.deleteProduct(productManager.getProductByPK(productId));
        resp.sendRedirect("list");
    }
}
