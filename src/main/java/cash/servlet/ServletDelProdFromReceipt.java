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

import static cash.service.ServiceReceiptProduct.getProducts;
import static cash.service.ServiceReceiptProduct.getReceiptProducts;

@WebServlet("/delProdFromReceipt")
public class ServletDelProdFromReceipt extends HttpServlet {
    ReceiptImpl receiptDao = new ReceiptImpl();
    ProductDaoImpl productDao = new ProductDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Receipt> receipts = receiptDao.findAll();
        request.getSession().setAttribute("receipts", receipts);
        List<Product> products = productDao.findAll();
        request.getSession().setAttribute("products", products);
        request.getRequestDispatcher("/WEB-INF/jsp/product/deleteProductFromReceipt.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String number = request.getParameter("number");
        Receipt receipt = receiptDao.findReceiptByNumber(number);
        Product[] products = getProducts(request);
        Transaction t = new Transaction();
        t.delProductFromReceipt(receipt, products);
        response.sendRedirect("/ServletBack");
    }
}
