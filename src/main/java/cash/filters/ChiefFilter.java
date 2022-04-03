package cash.filters;

import cash.entity.Role;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cash.entity.Role.CHIEF_CASHIER;

@WebFilter("/chief/*")
public class ChiefFilter implements Filter {
    public void init(FilterConfig config) {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        Role roleUser = Role.valueOf(String.valueOf(((HttpServletRequest) request).getSession().getAttribute("role")));
        if (roleUser == CHIEF_CASHIER) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).sendRedirect("login.jsp");
        }
    }
}
