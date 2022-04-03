package cash.servlet;

import cash.db.dao.impl.ProductDaoImpl;
import cash.db.dao.impl.ReceiptImpl;
import cash.entity.OperationStatus;
import cash.entity.Product;
import cash.entity.Receipt;
import cash.entity.ReceiptProducts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static cash.service.ServLetUtils.getIdLang;
import static cash.service.ServiceReceiptProduct.setAmountSumReceipt;

@WebServlet("/cashier/updateAmountReceipt")
public class ServletUpdateAmountReceipt extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ServletBack.class);
    ReceiptImpl receiptDao = ReceiptImpl.getInstance();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Servlet: ServletUpdateAmountReceipt. Method: Get");
        List<Receipt> receipts = receiptDao.findEntityByStatus(OperationStatus.CREATED);
        request.getSession().setAttribute("receipts", receipts);
        List<Product> products = ProductDaoImpl.getInstance().findAllByLang(getIdLang(request));
        request.getSession().setAttribute("products", products);
        request.getRequestDispatcher("/WEB-INF/jsp/receipt/chooseNumberReceipt.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Servlet: ServletUpdateAmountReceipt. Method: Post");
        if (request.getParameter("number") != null) {
            String number = request.getParameter("number");
            Receipt receipt = receiptDao.findReceiptByNumber(number);
            ArrayList<ReceiptProducts> products = receiptDao.getListProductsByIdReceiptLANG(receipt.getId(), getIdLang(request));
            receipt.setReceiptProducts(products);
            setAmountSumReceipt(receipt);
            request.getSession().setAttribute("receipt", receipt);
            request.getRequestDispatcher("/WEB-INF/jsp/receipt/updateReceipt.jsp")
                    .forward(request, response);
        }
    }
}
