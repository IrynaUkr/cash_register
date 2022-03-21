package cash.servlet;

import cash.db.dao.impl.ProductDaoImpl;
import cash.entity.Product;
import cash.entity.Receipt;
import cash.entity.ReceiptProducts;
import cash.db.dao.impl.TransactionDAOImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static cash.service.ServLetUtils.getIdLang;
import static cash.service.ServLetUtils.isValidate;
import static cash.service.ServiceReceiptProduct.createReceiptProduct;

@WebServlet("/cashier/servletSaveUpdateReceipt")
public class ServletSaveUpdateReceipt extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ServletBack.class);
    ProductDaoImpl productDao = ProductDaoImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Servlet: ServletSaveUpdateReceipt. Method: Get");
        if (isValidate(request)) {
            Product product = null;
            double amount = 0.0;
            if (request.getParameter("productNA") != null && (request.getParameter("amountNA") != null)) {
                product = productDao.findProductByNameLang(request.getParameter("productNA"), getIdLang(request));
                amount = Double.parseDouble(request.getParameter("amountNA"));
            } else if (request.getParameter("productCA") != null && request.getParameter("amountCA") != null) {
                product = productDao.findProductByCodeLang(request.getParameter("productCA"), getIdLang(request));
                amount = Double.parseDouble(request.getParameter("amountCA"));
            }
            if (product != null) {
                ReceiptProducts addReceiptProduct = createReceiptProduct(product, amount);
                Receipt receipt = (Receipt) request.getSession().getAttribute("receipt");
                TransactionDAOImpl.getInstance().updateAmountReceipt(receipt, addReceiptProduct);
                response.sendRedirect("/ServletBack");
            }
        } else {
            request.getRequestDispatcher("/WEB-INF/jsp/receipt/updateReceipt.jsp")
                    .forward(request, response);
        }
    }

}
