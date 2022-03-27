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
import java.util.List;

import static cash.ServLetUtils.getIdLang;
import static cash.ServLetUtils.isNameAndAmountValid;
import static cash.ServiceReceiptProduct.createReceiptProduct;
import static cash.ServiceReceiptProduct.setAmountSumReceipt;

@WebServlet("/cashier/addProductToReceiptList")
public class ServletOpenedReceiptAddProduct extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ServletOpenedReceiptAddProduct.class);
    ProductDaoImpl productDao = ProductDaoImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Servlet: ServletOpenedReceiptAddProduct. Method: Get");
        List<Product> products = productDao.findAllByLang(getIdLang(request));
        request.getSession().setAttribute("products", products);
        request.getRequestDispatcher("/WEB-INF/jsp/receipt/createReceipt.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Servlet: ServletOpenedReceiptAddProduct. Method: Post");
        if (isNameAndAmountValid(request)) {
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
                ReceiptProducts receiptProducts = createReceiptProduct(product, amount);
                Receipt receipt = (Receipt) request.getSession().getAttribute("receipt");
                boolean add = receipt.getReceiptProducts().add(receiptProducts);
                if (add) {
                    setAmountSumReceipt(receipt);
                    request.setAttribute("message", "product was added");
                } else {
                    request.setAttribute("message", " product was not added");
                }
                request.getSession().setAttribute("receipt", receipt);
                response.sendRedirect("/cashier/addProductToReceiptList");
            }
        } else {
            request.getRequestDispatcher("/WEB-INF/jsp/receipt/createReceipt.jsp")
                    .forward(request, response);
        }
    }
}
