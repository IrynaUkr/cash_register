package cash.servlet;

import cash.db.dao.impl.ReceiptImpl;
import cash.entity.OperationStatus;
import cash.entity.Receipt;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/closeReceipt")
public class ServletCloseReceipt extends HttpServlet {
    ReceiptImpl receiptDao = new ReceiptImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Receipt> receipts = receiptDao.findEntityByStatus(OperationStatus.CREATED);
        request.getSession().setAttribute("receipts", receipts);
        request.getRequestDispatcher("/WEB-INF/jsp/receipt/closeReceipt.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String number = request.getParameter("number");
        Receipt receipt = receiptDao.findReceiptByNumber(number);
        System.out.println(receipt);

        boolean closed = receiptDao.updateStatus(OperationStatus.CLOSED, receipt);
        if( closed){
            request.getSession().setAttribute("message", "receipt was closed!");
            response.sendRedirect("/ServletBack");
        }else {
            request.getSession().setAttribute("message", "receipt was not closed!");
            response.sendRedirect("/ServletBack");
        }
    }
}
