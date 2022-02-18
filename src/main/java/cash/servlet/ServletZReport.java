package cash.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static cash.service.ReportService.*;

@WebServlet("/chief/servletZReport")
public class ServletZReport extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/report/Zreport.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getXreport(request);
        if (setFiscalStatusReceipt()) {
            request.getSession().setAttribute("message1", "recepts were fiscalized");
        } else {
            request.getSession().setAttribute("message1", "recepts were not fiscalized");
        }
        if (setFiscalStatusPayment()) {
            request.getSession().setAttribute("message2", "payments were fiscalized");
        } else {
            request.getSession().setAttribute("message2", "payments were not fiscalized");
        }
        response.sendRedirect("/chief/servletZReport");

    }
}
