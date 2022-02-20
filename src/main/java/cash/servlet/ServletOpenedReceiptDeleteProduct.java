package cash.servlet;

import cash.db.dao.impl.ProductDaoImpl;
import cash.entity.Product;
import cash.entity.Receipt;
import cash.entity.ReceiptProducts;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static cash.service.ServiceReceiptProduct.createReceiptProduct;
import static cash.service.ServiceReceiptProduct.updateAmountSumReceipt;

@WebServlet("/cashier/deleteProductFromProductList")
public class ServletOpenedReceiptDeleteProduct extends HttpServlet {
    ProductDaoImpl productDao = new ProductDaoImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReceiptProducts receiptProducts = new ReceiptProducts();
        if (request.getParameter("productND") != null && (request.getParameter("amountND") != null)) {
            Product product = productDao.findProductByName(request.getParameter("productND"));
            Double amount = Double.valueOf(request.getParameter("amountND"));
            receiptProducts = createReceiptProduct(request, product, amount);
        } else if (request.getParameter("productCD") != null && request.getParameter("amountCD") != null) {
            Product product = productDao.findProductByCode(request.getParameter("productCD"));
            Double amount = Double.valueOf(request.getParameter("amountCD"));
            receiptProducts = createReceiptProduct(request, product, amount);
        }
        Receipt receipt = (Receipt) request.getSession().getAttribute("receipt");
        boolean remove = receipt.getReceiptProducts().remove(receiptProducts);
        if (remove) {
            updateAmountSumReceipt(receipt);
            request.setAttribute("message", "product was removed");
        } else {
            request.setAttribute("message", " product was not removed");
        }
        request.getSession().setAttribute("receipt", receipt);
        response.sendRedirect("/cashier/addProductToReceiptList");
    }
}