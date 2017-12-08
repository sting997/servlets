package pl.epoint.mkowalczyk.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class ServletSample extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        java.io.Writer writer = resp.getWriter();
        writer.append("<html>");
        writer.append("<body>");
        writer.append("<h1>Hello world1!</h1>");
        writer.append("</body>");
        writer.append("</html>");
    }
}
