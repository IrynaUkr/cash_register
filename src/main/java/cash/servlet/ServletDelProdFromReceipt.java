package cash.servlet;

import cash.db.dao.impl.ProductDaoImpl;
import cash.db.dao.impl.ReceiptImpl;
import cash.entity.OperationStatus;
import cash.entity.Product;
import cash.entity.Receipt;
import cash.db.dao.impl.TransactionDAOImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

import static cash.service.ServLetUtils.*;

@WebServlet("/chief/delProdFromReceipt")
public class ServletDelProdFromReceipt extends HttpServlet {
    ReceiptImpl receiptDao = ReceiptImpl.getInstance();
    ProductDaoImpl productDao = ProductDaoImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Receipt> receipts = receiptDao.findEntityByStatus(OperationStatus.CREATED);
        request.getSession().setAttribute("receipts", receipts);
        List<Product> products = productDao.findAllByLang(getIdLang(request));
        request.getSession().setAttribute("products", products);
        request.getRequestDispatcher("/WEB-INF/jsp/product/deleteProductFromReceipt.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isValidateName(request)) {
            Product product = new Product();
            if (request.getParameter("productNA") != null) {
                product = productDao.findProductByNameLang(request.getParameter("productNA"), getIdLang(request));
            } else if (request.getParameter("productCA") != null) {
                product = productDao.findProductByCodeLang(request.getParameter("productCA"), getIdLang(request));
            }
            String number =  request.getParameter("number");
            Receipt receipt = receiptDao.findReceiptByNumber(number);
            TransactionDAOImpl.getInstance().delProductFromReceipt(receipt, product);
            response.sendRedirect("/ServletBack");
        }
    }
}
