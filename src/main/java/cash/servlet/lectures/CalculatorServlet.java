package cash.servlet.lectures;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/calc")
public class CalculatorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message;

        int result = 0;
        try {
            int a = Integer.parseInt(request.getParameter("a"));
            int b = Integer.parseInt(request.getParameter("b"));
            if (request.getParameter("plus") != null) {
                result = a + b;
            } else if (request.getParameter("minus") != null) {
                result = a - b;
            } else if (request.getParameter("times") != null) {
                result = a * b;
            } else if (request.getParameter("div") != null) {
                if (b != 0) {
                    result = a / b;
                } else {
                    throw new IllegalArgumentException() ;
                }
            }
            message = "RESULT: " + result;
        } catch (IllegalArgumentException e) {
            message = "Error:" + e.getMessage();
        }
        response.getWriter().append(message);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
