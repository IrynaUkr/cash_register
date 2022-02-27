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

import static cash.service.ServiceForServ.getId_lang;
import static cash.service.ServiceForServ.isValidate;
import static cash.service.ServiceReceiptProduct.createReceiptProduct;
import static cash.service.ServiceReceiptProduct.updateAmountSumReceipt;

@WebServlet("/cashier/addProductToReceiptList")
public class ServletOpenedReceiptAddProduct extends HttpServlet {
    ProductDaoImpl productDao = new ProductDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = productDao.findAllByLang(getId_lang(request));
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
                product = productDao.findProductByNameLang(request.getParameter("productNA"), getId_lang(request));
                amount = Double.parseDouble(request.getParameter("amountNA"));
            } else if (request.getParameter("productCA") != null && request.getParameter("amountCA") != null) {
                product = productDao.findProductByCodeLang(request.getParameter("productCA"), getId_lang(request));
                amount = Double.parseDouble(request.getParameter("amountCA"));
            }
            if (product != null) {
                ReceiptProducts receiptProducts = createReceiptProduct(request, product, amount);
                Receipt receipt = (Receipt) request.getSession().getAttribute("receipt");
                boolean add = receipt.getReceiptProducts().add(receiptProducts);
                if (add) {
                    updateAmountSumReceipt(receipt);
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
