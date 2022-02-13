package cash.servlet;

import cash.db.dao.impl.ProductDaoImpl;
import cash.db.dao.impl.ReceiptImpl;
import cash.entity.OperationStatus;
import cash.entity.Product;
import cash.entity.Receipt;
import cash.entity.ReceiptProducts;
import cash.service.Transaction;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

import static cash.service.ServiceReceiptProduct.getReceiptProducts;

@WebServlet("/updateAmountReceipt")
public class ServletUpdateAmountReceipt extends HttpServlet {
    ReceiptImpl receiptDao = new ReceiptImpl();
    ProductDaoImpl productDao = new ProductDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Receipt> receipts = receiptDao.findEntityByStatus(OperationStatus.CREATED);
        List<Product> products = productDao.findAll();
        request.getSession().setAttribute("products", products);
        request.getSession().setAttribute("receipts", receipts);
        request.getRequestDispatcher("/WEB-INF/jsp/receipt/updateAmountReceipt.jsp")
                .forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String number = request.getParameter("number");
        Receipt receipt = receiptDao.findReceiptByNumber(number);
        ReceiptProducts[] reseiptProducts = getReceiptProducts(request);
        Transaction t = new Transaction();
        t.updateAmountReceipt(receipt, reseiptProducts);
        response.sendRedirect("/ServletBack");
    }
}
