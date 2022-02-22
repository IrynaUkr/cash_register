package cash.servlet;

import cash.db.dao.impl.ReceiptImpl;
import cash.entity.OperationStatus;
import cash.entity.Receipt;
import cash.entity.ReceiptProducts;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static cash.service.ServiceForServ.getId_lang;
import static cash.service.ServiceReceiptProduct.updateAmountSumReceipt;

@WebServlet("/cashier/printReceipt")
public class ServletPrintReceipt extends HttpServlet {
    ReceiptImpl receiptDao = new ReceiptImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id_lang = getId_lang(request);
        List<Receipt> receipts = receiptDao.findAll();
        request.getSession()
                .setAttribute("receipts", receipts);
        request.getRequestDispatcher("/WEB-INF/jsp/receipt/printReceipt.jsp")
                .forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id_lang = getId_lang(request);
        String number = request.getParameter("number");
        Receipt receipt = receiptDao.findReceiptByNumber(number);
        ArrayList<ReceiptProducts> products = receiptDao.getListProductsByIdReceiptLANG(receipt.getId(), id_lang);
        receipt.setReceiptProducts(products);
        updateAmountSumReceipt(receipt);
        request.getSession().setAttribute("receipt",receipt);
        request.getRequestDispatcher("/WEB-INF/jsp/receipt/showReceipt.jsp")
                .forward(request, response);

    }
}
