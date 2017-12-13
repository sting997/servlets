package pl.epoint.mkowalczyk.servlets;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = productManager.getProductsList();
        req.setAttribute("products", products);
        req.getRequestDispatcher("/WEB-INF/list.jsp").forward(req, resp);
    }

    //todo przemyslec czy tak ma byc
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer productId = Integer.valueOf(req.getParameter("productId"));
        productManager.deleteProduct(productManager.getProductByPK(productId));
        resp.sendRedirect("list");
    }
}
