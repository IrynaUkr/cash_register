package cash.servlet;

import cash.db.dao.impl.ReceiptImpl;
import cash.entity.OperationStatus;
import cash.entity.Receipt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/cashier/closeReceipt")
public class ServletCloseReceipt extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ServletCloseReceipt.class);
    ReceiptImpl receiptDao = ReceiptImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Servlet: /cashier/closeReceipt. Method: Get");
        List<Receipt> receipts = receiptDao.findEntityByStatus(OperationStatus.CREATED);
        request.getSession().setAttribute("receipts", receipts);
        request.getRequestDispatcher("/WEB-INF/jsp/receipt/closeReceipt.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Servlet: /cashier/closeReceipt. Method: Post");
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
