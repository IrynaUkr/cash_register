package cash.servlet.filters;

import cash.entity.Role;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cash.entity.Role.ADMIN;
import static cash.entity.Role.CASHIER;

@WebFilter("/cashier/*")
public class CashierFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        Role roleUser = Role.valueOf(String.valueOf(((HttpServletRequest) request).getSession().getAttribute("role")));
        if (roleUser == CASHIER) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).sendRedirect("login.jsp");
        }
    }

}
