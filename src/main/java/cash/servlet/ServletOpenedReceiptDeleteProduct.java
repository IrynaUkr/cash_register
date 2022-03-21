package cash.servlet;

import cash.db.dao.impl.ProductDaoImpl;
import cash.entity.Product;
import cash.entity.Receipt;
import cash.entity.ReceiptProducts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static cash.service.ServLetUtils.getIdLang;
import static cash.service.ServiceReceiptProduct.createReceiptProduct;
import static cash.service.ServiceReceiptProduct.setAmountSumReceipt;

@WebServlet("/cashier/deleteProductFromProductList")
public class ServletOpenedReceiptDeleteProduct extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ServletOpenedReceiptDeleteProduct.class);
    ProductDaoImpl productDao = ProductDaoImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Servlet: ServletOpenedReceiptDeleteProduct. Method: Get");
        Product product = null;
        Double amount = null;
        if (isValidateDel(request)) {
            if (request.getParameter("productND") != null && (request.getParameter("amountND") != null)) {
                product = productDao.findProductByNameLang(request.getParameter("productND"), getIdLang(request));
                amount = Double.valueOf(request.getParameter("amountND"));
            } else if (request.getParameter("productCD") != null && request.getParameter("amountCD") != null) {
                product = productDao.findProductByCodeLang(request.getParameter("productCD"), getIdLang(request));
                amount = Double.valueOf(request.getParameter("amountCD"));
            }
            ReceiptProducts receiptProducts = createReceiptProduct( product, amount);
            Receipt receipt = (Receipt) request.getSession().getAttribute("receipt");
            boolean remove = receipt.getReceiptProducts().remove(receiptProducts);
            if (remove) {
                setAmountSumReceipt(receipt);
                request.setAttribute("message", "product was removed");
            } else {
                request.setAttribute("message", " product was not removed");
            }
            request.getSession().setAttribute("receipt", receipt);
            response.sendRedirect("/cashier/addProductToReceiptList");
        }else{
            request.getRequestDispatcher("/WEB-INF/jsp/receipt/createReceipt.jsp")
                    .forward(request, response);
        }
    }

    private boolean isValidateDel(HttpServletRequest request) {
        logger.info("Servlet: ServletOpenedReceiptDeleteProduct. Method: Get");
        return request.getParameter("productND") != ""
                && request.getParameter("amountND") != ""
                && request.getParameter("productCD") != ""
                && request.getParameter("amountCD") != "";
    }
}
