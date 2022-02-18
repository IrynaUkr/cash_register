package cash.servlet.filters;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebFilter
public class EncodingFilter implements Filter {
    private String encoding;
    public void init(FilterConfig config) throws ServletException {
        // читаем из конфигурации
        encoding = config.getInitParameter("requestEncoding");
        // если не установлена — устанавливаем UTF-8
        if (encoding == null) encoding = "UTF-8";
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }

}
