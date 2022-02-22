package cash.servlet;

import cash.db.dao.impl.ProductDaoImpl;
import cash.entity.Product;
import cash.entity.Receipt;
import cash.entity.ReceiptProducts;
import cash.service.ServiceReceiptProduct;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

import static cash.service.ServiceForServ.getId_lang;
import static cash.service.ServiceReceiptProduct.createReceiptProduct;
import static cash.service.ServiceReceiptProduct.updateAmountSumReceipt;

@WebServlet("/cashier/addProductToReceiptList")
public class ServletOpenedReseiptAddProduct extends HttpServlet {
    ProductDaoImpl productDao = new ProductDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id_lang = getId_lang(request);
        List<Product> products = productDao.findAllByLang(id_lang);
        request.getSession().setAttribute("products", products);
        request.getRequestDispatcher("/WEB-INF/jsp/receipt/createReceipt.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id_lang = getId_lang(request);
        ReceiptProducts receiptProducts = new ReceiptProducts();
        if (request.getParameter("productNA") != null && (request.getParameter("amountNA") != null)) {
            Product product = productDao.findProductByNameLang(request.getParameter("productNA"), id_lang);
            Double amount = Double.valueOf(request.getParameter("amountNA"));
            receiptProducts = createReceiptProduct(request, product, amount);
        } else if (request.getParameter("productCA") != null && request.getParameter("amountCA") != null) {
            Product product = productDao.findProductByCodeLang(request.getParameter("productCA"), id_lang);
            Double amount = Double.valueOf(request.getParameter("amountCA"));
            receiptProducts = createReceiptProduct(request, product, amount);
        }
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


}
