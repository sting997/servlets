package pl.epoint.mkowalczyk.servlets;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
@WebServlet(urlPatterns = "/login")
public class LoginFilter extends HttpServlet implements Filter {
    private final String LOGIN_JSP_PATH = "/WEB-INF/login.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = (String) req.getAttribute("action");
        if (action != null && action.equals("login")){
            req.getRequestDispatcher(LOGIN_JSP_PATH).forward(req, resp);
        }
        else if (action != null && action.equals("logout")){
            req.getSession().setAttribute("userId", null);
            req.getSession().invalidate();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("user");
        String password = req.getParameter("password");
        if ("user".equals(user) && "123456".equals(password)){
            req.getSession().setAttribute("userId", Integer.valueOf(1));
            resp.sendRedirect(req.getContextPath() +"/list");
        }
        else
            resp.sendRedirect(req.getContextPath() + "/login");
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        if (userId == null) {
            if (req.getServletPath().equals("/login")){
                req.setAttribute("action", "login");
                chain.doFilter(request, response);
            }
            else {
                req.setAttribute("action", "login");
                resp.sendRedirect(req.getContextPath() + "/login");
            }
        }
        else {
            chain.doFilter(request, response);
        }
    }


    @Override
    public void destroy() {

    }
}
