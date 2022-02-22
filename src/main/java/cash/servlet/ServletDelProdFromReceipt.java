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

import static cash.service.ServiceForServ.getId_lang;
import static cash.service.ServiceReceiptProduct.*;
import static cash.service.ServiceReceiptProduct.createReceiptProduct;

@WebServlet("/chief/delProdFromReceipt")
public class ServletDelProdFromReceipt extends HttpServlet {
    ReceiptImpl receiptDao = new ReceiptImpl();
    ProductDaoImpl productDao = new ProductDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Receipt> receipts = receiptDao.findAll();
        request.getSession().setAttribute("receipts", receipts);
        int id_lang = getId_lang(request);
        List<Product> products = productDao.findAllByLang(id_lang);
        request.getSession().setAttribute("products", products);
        request.getRequestDispatcher("/WEB-INF/jsp/product/deleteProductFromReceipt.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id_lang = getId_lang(request);
        Product product= new Product();
        if (request.getParameter("productNA") != null ) {
            product = productDao.findProductByNameLang(request.getParameter("productNA"),id_lang);
        } else if (request.getParameter("productCA") != null) {
           product = productDao.findProductByCodeLang(request.getParameter("productCA"), id_lang);
        }
        Receipt receipt = (Receipt) request.getSession().getAttribute("receipt");
        Transaction t = new Transaction();
        t.delProductFromReceipt(receipt, product);
        response.sendRedirect("/ServletBack");
    }
}
