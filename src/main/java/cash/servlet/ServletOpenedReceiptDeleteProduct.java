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

import static cash.service.ServiceForServ.getId_lang;
import static cash.service.ServiceReceiptProduct.createReceiptProduct;
import static cash.service.ServiceReceiptProduct.updateAmountSumReceipt;

@WebServlet("/cashier/deleteProductFromProductList")
public class ServletOpenedReceiptDeleteProduct extends HttpServlet {
    ProductDaoImpl productDao = new ProductDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = null;
        Double amount = null;
        if (isValidateDel(request)) {
            if (request.getParameter("productND") != null && (request.getParameter("amountND") != null)) {
                product = productDao.findProductByNameLang(request.getParameter("productND"), getId_lang(request));
                amount = Double.valueOf(request.getParameter("amountND"));
            } else if (request.getParameter("productCD") != null && request.getParameter("amountCD") != null) {
                product = productDao.findProductByCodeLang(request.getParameter("productCD"), getId_lang(request));
                amount = Double.valueOf(request.getParameter("amountCD"));
            }
            ReceiptProducts receiptProducts = createReceiptProduct(request, product, amount);
            Receipt receipt = (Receipt) request.getSession().getAttribute("receipt");
            boolean remove = receipt.getReceiptProducts().remove(receiptProducts);
            if (remove) {
                updateAmountSumReceipt(receipt);
                request.setAttribute("message", "product was removed");
            } else {
                request.setAttribute("message", " product was not removed");
            }
            request.getSession().setAttribute("receipt", receipt);
            response.sendRedirect("/cashier/addProductToReceiptList");
        }else{
            request.getRequestDispatcher("/WEB-INF/jsp/receipt/createReceipt.jsp")
                    .forward(request, response);
        }
    }

    private boolean isValidateDel(HttpServletRequest request) {
        return request.getParameter("productND") != ""
                && request.getParameter("amountND") != ""
                && request.getParameter("productCD") != ""
                && request.getParameter("amountCD") != "";
    }
}
