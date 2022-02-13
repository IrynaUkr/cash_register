package cash.servlet;

import cash.db.dao.impl.ReceiptImpl;
import cash.entity.Receipt;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ServletReport", value = "/ServletReport")
public class ServletReport extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReceiptImpl receiptDao = new ReceiptImpl();
        List<Receipt> receipts = receiptDao.findAll();
        request.getSession().setAttribute("receipts", receipts);
        request.getRequestDispatcher("/WEB-INF/jsp/product/report/Xreport.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
