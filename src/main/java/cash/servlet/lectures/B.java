package cash.servlet.lectures;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/b")
public class B extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("b - doGet()");
      //  response.getWriter().append("served at b: ").append(request.getContextPath()).append("by "+getServletName());
     //   getServletContext().getRequestDispatcher("/a").forward(request,response);
    //    getServletContext().getRequestDispatcher("/a").include(request,response);
        String attVal = request.getParameter("data");
        if (attVal == null){
            attVal="no data";
        }
        HttpSession session = request.getSession();
        session.setAttribute("data",attVal);
        response.sendRedirect("a");
    }


}
