package cash.servlet;

import cash.db.dao.impl.PaymentDaoImpl;
import cash.db.dao.impl.ReceiptImpl;
import cash.entity.OperationType;
import cash.entity.Payment;
import cash.entity.Receipt;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import static cash.service.ReportService.getXreport;

@WebServlet("/chief/servletXReport")
public class ServletXReport extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReceiptImpl receiptDao = new ReceiptImpl();
        List<Receipt> receipts = receiptDao.findAll();
        request.getSession().setAttribute("receipts", receipts);
        request.getRequestDispatcher("/WEB-INF/jsp/report/Xreport.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        getXreport(request);
        response.sendRedirect("/chief/servletXReport");

    }


}
