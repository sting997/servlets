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
    private static final String LOGIN_JSP_PATH = "/WEB-INF/login.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (isLoginAction(req)){
            redirectToLogInPage(req, resp);
        }
        else if (isLogoutAction(req)){
            performLogOut(req, resp);
        }
    }

    private void performLogOut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().setAttribute("userId", null);
        req.getSession().invalidate();
        System.out.println("logged out");
        resp.sendRedirect(req.getContextPath() + "/login");
    }

    private void redirectToLogInPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(LOGIN_JSP_PATH).forward(req, resp);
    }

    private boolean isLogoutAction(HttpServletRequest req) {
        String action = req.getParameter("action");
        return action != null && action.equals("logout");
    }

    private boolean isLoginAction(HttpServletRequest req) {
        String action = (String) req.getAttribute("action");
        return action != null && action.equals("login");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("user");
        String password = req.getParameter("password");
        if (validateUser(user, password)){
            redirectLoggedInUser(req, resp);
        }
        else
            resp.sendRedirect(req.getContextPath() + "/login");
    }

    private void redirectLoggedInUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().setAttribute("userId", Integer.valueOf(1));
        resp.sendRedirect(req.getContextPath() +"/list");
    }

    private boolean validateUser(String user, String password) {
        return "user".equals(user) && "123456".equals(password);
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        if (isUserNotLoggedIn(userId)) {
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

    private boolean isUserNotLoggedIn(Integer userId) {
        return userId == null;
    }
}
