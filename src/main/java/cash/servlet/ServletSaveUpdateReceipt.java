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

import static cash.service.ServiceForServ.getId_lang;
import static cash.service.ServiceForServ.isValidate;
import static cash.service.ServiceReceiptProduct.createReceiptProduct;

@WebServlet("/cashier/servletSaveUpdateReceipt")
public class ServletSaveUpdateReceipt extends HttpServlet {
    ProductDaoImpl productDao = new ProductDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isValidate(request)) {
            Product product = null;
            double amount = 0.0;
            if (request.getParameter("productNA") != null && (request.getParameter("amountNA") != null)) {
                product = productDao.findProductByNameLang(request.getParameter("productNA"),getId_lang(request));
                amount = Double.parseDouble(request.getParameter("amountNA"));
            } else if (request.getParameter("productCA") != null && request.getParameter("amountCA") != null) {
                product = productDao.findProductByCodeLang(request.getParameter("productCA"),getId_lang(request));
                amount = Double.parseDouble(request.getParameter("amountCA"));
            }
            if (product != null) {
                ReceiptProducts addReceiptProduct = createReceiptProduct(request, product, amount);
                Receipt receipt = (Receipt) request.getSession().getAttribute("receipt");
                Transaction t = new Transaction();
                t.updateAmountReceipt(receipt, addReceiptProduct);
                response.sendRedirect("/ServletBack");
            }
        } else {
            request.getRequestDispatcher("/WEB-INF/jsp/receipt/updateReceipt.jsp")
                    .forward(request, response);
        }
    }

}
