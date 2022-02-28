package cash.servlet;

import cash.db.dao.impl.ProductDaoImpl;
import cash.db.dao.impl.ReceiptImpl;
import cash.entity.OperationStatus;
import cash.entity.Product;
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

@WebServlet("/cashier/updateAmountReceipt")
public class ServletUpdateAmountReceipt extends HttpServlet {
    ReceiptImpl receiptDao = new ReceiptImpl();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Receipt> receipts = receiptDao.findEntityByStatus(OperationStatus.CREATED);
        request.getSession().setAttribute("receipts", receipts);
        List<Product> products = new ProductDaoImpl().findAllByLang(getId_lang(request));
        request.getSession().setAttribute("products", products);
        request.getRequestDispatcher("/WEB-INF/jsp/receipt/chooseNumberReceipt.jsp")
                .forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("number") != null) {
            String number = request.getParameter("number");
            Receipt receipt = receiptDao.findReceiptByNumber(number);
            ArrayList<ReceiptProducts> products = receiptDao.getListProductsByIdReceiptLANG(receipt.getId(), getId_lang(request));
            receipt.setReceiptProducts(products);
            updateAmountSumReceipt(receipt);
            request.getSession().setAttribute("receipt", receipt);
            request.getRequestDispatcher("/WEB-INF/jsp/receipt/updateReceipt.jsp")
                    .forward(request, response);
        }
    }
}
