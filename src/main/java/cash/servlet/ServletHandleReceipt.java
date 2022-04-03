package cash.servlet;

import cash.db.dao.impl.ReceiptImpl;
import cash.entity.Receipt;
import cash.entity.ReceiptProducts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static cash.service.ServLetUtils.getIdLang;
import static cash.service.ServiceReceiptProduct.setAmountSumReceipt;

@WebServlet("/cashier/printReceipt")
public class ServletHandleReceipt extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ServletHandleReceipt.class);
    ReceiptImpl receiptDao = ReceiptImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Servlet: ServletHandleReceipt. Method: Get");
        List<Receipt> receipts = receiptDao.findAll();
        request.getSession()
                .setAttribute("receipts", receipts);
        request.getRequestDispatcher("/WEB-INF/jsp/receipt/handleReceipt.jsp")
                .forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Servlet: ServletHandleReceipt. Method: Post");
        if (request.getParameter("number") != "") {
            Receipt receipt = receiptDao.findReceiptByNumber(request.getParameter("number"));
            ArrayList<ReceiptProducts> products = receiptDao.getListProductsByIdReceiptLANG(receipt.getId(), getIdLang(request));
            receipt.setReceiptProducts(products);
            setAmountSumReceipt(receipt);
            request.getSession().setAttribute("receipt", receipt);
            request.getRequestDispatcher("/WEB-INF/jsp/receipt/showReceipt.jsp")
                    .forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/jsp/receipt/handleReceipt.jsp")
                    .forward(request, response);
        }
    }
}
