package cash.servlet;

import cash.db.dao.impl.PaymentDaoImpl;
import cash.db.dao.impl.ReceiptImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Date;

import static cash.ReportUtils.*;

@WebServlet("/chief/servletZReport")
public class ServletZReport extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ServletZReport.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Servlet: ServletZReport. Method: Get");
        request.getRequestDispatcher("/WEB-INF/jsp/report/Zreport.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Servlet: ServletZReport. Method: Post");
        if (request.getParameter("date") != "") {
            Date date = Date.valueOf(request.getParameter("date"));
            getXReport(request, date);
            if (ReceiptImpl.getInstance().setFiscalStatusReceipt()) {
                request.getSession().setAttribute("messageReceipt", "receipts were fiscalized");
            } else {
                request.getSession().setAttribute("messageReceipt", "receipts were not fiscalized");
            }
            if (PaymentDaoImpl.getInstance().setFiscalStatusPayment()) {
                request.getSession().setAttribute("messagePayment", "payments were fiscalized");
            } else {
                request.getSession().setAttribute("messagePayment", "payments were not fiscalized");
            }
            response.sendRedirect("/chief/servletZReport");

        } else {
            request.getSession().setAttribute("message", "choose date");
            request.getRequestDispatcher("/WEB-INF/jsp/startChief.jsp").forward(request, response);
        }
    }
}
