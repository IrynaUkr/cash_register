package cash.servlet;

import cash.db.dao.impl.PaymentDaoImpl;
import cash.db.dao.impl.ReceiptImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Date;

import static cash.service.ReportService.*;

@WebServlet("/chief/servletZReport")
public class ServletZReport extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/report/Zreport.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("date") != "") {
            Date date = Date.valueOf(request.getParameter("date"));
            getXReport(request, date);
            if (new ReceiptImpl().setFiscalStatusReceipt()) {
                request.getSession().setAttribute("message1", "receipts were fiscalized");
                System.out.println("receipts were fiscalized");
            } else {
                request.getSession().setAttribute("message1", "receipts were not fiscalized");
                System.out.println("receipts were not fiscalized");
            }
            if (new PaymentDaoImpl().setFiscalStatusPayment()) {
                request.getSession().setAttribute("message2", "payments were fiscalized");
            } else {
                request.getSession().setAttribute("message2", "payments were not fiscalized");
            }
            response.sendRedirect("/chief/servletZReport");

        } else {
            request.getSession().setAttribute("message", "choose date");
            request.getRequestDispatcher("/WEB-INF/jsp/startChief.jsp").forward(request, response);
        }
    }
}
