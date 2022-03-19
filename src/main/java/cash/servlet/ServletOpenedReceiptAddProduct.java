package cash.servlet;

import cash.db.dao.impl.ProductDaoImpl;
import cash.entity.Product;
import cash.entity.Receipt;
import cash.entity.ReceiptProducts;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

import static cash.service.ServLetUtils.getIdLang;
import static cash.service.ServLetUtils.isValidate;
import static cash.service.ServiceReceiptProduct.createReceiptProduct;
import static cash.service.ServiceReceiptProduct.setAmountSumReceipt;

@WebServlet("/cashier/addProductToReceiptList")
public class ServletOpenedReceiptAddProduct extends HttpServlet {
    ProductDaoImpl productDao = ProductDaoImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = productDao.findAllByLang(getIdLang(request));
        request.getSession().setAttribute("products", products);
        request.getRequestDispatcher("/WEB-INF/jsp/receipt/createReceipt.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
