package cash.servlet;

import cash.db.dao.impl.ProductDaoImpl;
import cash.entity.Product;
import cash.entity.Receipt;
import cash.entity.ReceiptProducts;
import cash.service.Transaction;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static cash.service.ServiceReceiptProduct.createReceiptProduct;

@WebServlet("/cashier/servletSaveUpdateReceipt")
public class ServletSaveUpdateReceipt extends HttpServlet {
    ProductDaoImpl productDao = new ProductDaoImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReceiptProducts addReceiptProduct = new ReceiptProducts();
        if (request.getParameter("productNA") != null && (request.getParameter("amountNA") != null)) {
            Product product = productDao.findProductByName(request.getParameter("productNA"));
            Double amount = Double.valueOf(request.getParameter("amountNA"));
            addReceiptProduct = createReceiptProduct(request, product, amount);
        } else if (request.getParameter("productCA") != null && request.getParameter("amountCA") != null) {
            Product product = productDao.findProductByCode(request.getParameter("productCA"));
            Double amount = Double.valueOf(request.getParameter("amountCA"));
            addReceiptProduct = createReceiptProduct(request, product, amount);
        }
        Receipt receipt = (Receipt) request.getSession().getAttribute("receipt");
        Transaction t = new Transaction();
        t.updateAmountReceipt(receipt,addReceiptProduct);
        response.sendRedirect("/ServletBack");

    }

}
