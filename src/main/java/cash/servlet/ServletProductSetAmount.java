package cash.servlet;

import cash.db.dao.ProductDao;
import cash.db.dao.impl.PaymentDaoImpl;
import cash.db.dao.impl.ProductDaoImpl;
import cash.entity.Product;
import cash.entity.Receipt;
import cash.entity.ReceiptProducts;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static cash.service.ServiceReceiptProduct.createReceiptProduct;
import static cash.service.ServiceReceiptProduct.updateAmountSumReceipt;

@WebServlet("/merch/setAmountProduct")
public class ServletProductSetAmount extends HttpServlet {
    ProductDaoImpl productDao = new ProductDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Product> products = productDao.findAll();
        request.getSession()
                .setAttribute("products", products);
        request.getRequestDispatcher("/WEB-INF/jsp/product/updateDeleteProduct.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product= null;
        Double amount =0.0;
        if (request.getParameter("productNA") != null && (request.getParameter("amountNA") != null)) {
            product = productDao.findProductByName(request.getParameter("productNA"));
            System.out.println(product +"found");
            amount = Double.valueOf(request.getParameter("amountNA"));
        } else if (request.getParameter("productCA") != null && request.getParameter("amountCA") != null) {
            product = productDao.findProductByCode(request.getParameter("productCA"));
             amount = Double.valueOf(request.getParameter("amountCA"));
        }
        boolean isUpdate = productDao.updateAmount(product, amount);
        if(isUpdate){
            request.getSession().setAttribute("message", "set new amount");
        }else{
            request.getSession().setAttribute("message", "did not set new amount");
        }
        response.sendRedirect("/ServletBack");
    }
}
