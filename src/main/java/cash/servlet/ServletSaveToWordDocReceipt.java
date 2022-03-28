package cash.servlet;

import cash.db.dao.impl.ReceiptImpl;
import cash.entity.Receipt;
import cash.entity.ReceiptProducts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;

import java.util.ArrayList;

import static cash.service.ServLetUtils.getIdLang;
import static cash.service.ServiceReceiptProduct.setAmountSumReceipt;

@WebServlet("/cashier/servletSaveToWordDocReceipt")
public class ServletSaveToWordDocReceipt extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ServletSaveToWordDocReceipt.class);
    ReceiptImpl receiptDao = ReceiptImpl.getInstance();


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Servlet: ServletSaveToWordDocReceipt. Method: Post");
        int id_lang = getIdLang(request);
        String number = request.getParameter("number");
        Receipt receipt = receiptDao.findReceiptByNumber(number);
        ArrayList<ReceiptProducts> products = receiptDao.getListProductsByIdReceiptLANG(receipt.getId(), id_lang);
        receipt.setReceiptProducts(products);
        setAmountSumReceipt(receipt);
        request.getSession().setAttribute("receipt", receipt);
        request.getRequestDispatcher("/WEB-INF/jsp/receipt/saveToWordDocReceipt.jsp")
                .forward(request, response);

    }


}
