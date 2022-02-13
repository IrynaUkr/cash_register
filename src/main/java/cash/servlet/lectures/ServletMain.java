package cash.servlet.lectures;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet
public class ServletMain extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("doGet in main");
    }

    @Override
    public void init() throws ServletException {
        System.out.println("Main INIT");
        String initParam = getServletConfig().getInitParameter("PARAM_1");
        System.out.println(initParam);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
