package cash.servlet.lectures;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/a")
public class A extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("a -> do get");
        HttpSession session= request.getSession(true);
        String attVal = (String)session.getAttribute("data");
        response.getWriter()
                .append("served at a : ")
                .append(request.getContextPath())
                .append("by "+getServletName())
                .append(" data = " + attVal);;
        System.out.println(attVal);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
