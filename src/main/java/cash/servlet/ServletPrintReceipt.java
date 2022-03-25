package cash.servlet;

import cash.db.dao.impl.ReceiptImpl;
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

@WebServlet("/cashier/printReceipt")
public class ServletPrintReceipt extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ServletPrintReceipt.class);
    ReceiptImpl receiptDao = ReceiptImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Servlet: ServletPrintReceipt. Method: Get");
        List<Receipt> receipts = receiptDao.findAll();
        request.getSession()
                .setAttribute("receipts", receipts);
        request.getRequestDispatcher("/WEB-INF/jsp/receipt/printReceipt.jsp")
                .forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Servlet: ServletPrintReceipt. Method: Post");
        int id_lang = getIdLang(request);
        String number = request.getParameter("number");
        Receipt receipt = receiptDao.findReceiptByNumber(number);
        ArrayList<ReceiptProducts> products = receiptDao.getListProductsByIdReceiptLANG(receipt.getId(), id_lang);
        receipt.setReceiptProducts(products);
        setAmountSumReceipt(receipt);
        request.getSession().setAttribute("receipt", receipt);
        request.getRequestDispatcher("/WEB-INF/jsp/receipt/showReceipt.jsp")
                .forward(request, response);

    }
}
