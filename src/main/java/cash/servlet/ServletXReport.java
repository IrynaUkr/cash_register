package cash.servlet;

import cash.db.dao.impl.ReceiptImpl;
import cash.entity.Receipt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

import static cash.service.ReportUtils.getXReport;

@WebServlet("/chief/servletXReport")
public class ServletXReport extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ServletXReport.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Servlet: ServletXReport. Method: Get");
        List<Receipt> receipts = ReceiptImpl.getInstance().findAll();
        request.getSession().setAttribute("receipts", receipts);
        request.getRequestDispatcher("/WEB-INF/jsp/report/Xreport.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Servlet: ServletXReport. Method: Post");
        if (request.getParameter("date") != "") {
            Date date = Date.valueOf(request.getParameter("date"));
            getXReport(request, date);
            response.sendRedirect("/chief/servletXReport");
        } else {
            request.setAttribute("message", "choose date");
            request.getRequestDispatcher("/WEB-INF/jsp/startChief.jsp").forward(request, response);
        }
    }
}
