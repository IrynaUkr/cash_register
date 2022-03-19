package cash.servlet;

import cash.db.dao.impl.ReceiptImpl;
import cash.entity.OperationStatus;
import cash.entity.Receipt;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/chief/canselReceipt")
public class ServletCanselReceipt extends HttpServlet {
    ReceiptImpl receiptDao = ReceiptImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Receipt> receipts = receiptDao.findEntityByStatus(OperationStatus.CREATED);
        request.getSession().setAttribute("receipts", receipts);
        request.getRequestDispatcher("/WEB-INF/jsp/receipt/canselReceipt.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("number") != "") {
            Receipt receipt = receiptDao.findReceiptByNumber(request.getParameter("number"));
            if (receiptDao.delete(receipt)) {
                request.getSession().setAttribute("message", "receipt was cancelled!");
                response.sendRedirect("/ServletBack");
            } else {
                request.getSession().setAttribute("message", "receipt was not cancelled!");
                response.sendRedirect("/ServletBack");
            }
        } else {
            request.setAttribute("message", "required fields are empty");
            request.getRequestDispatcher("/WEB-INF/jsp/receipt/canselReceipt.jsp").forward(request, response);
        }
    }
}
