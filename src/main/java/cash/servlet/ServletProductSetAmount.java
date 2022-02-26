package cash.servlet;

import cash.db.dao.impl.ProductDaoImpl;
import cash.entity.Product;
import cash.service.ServiceReceiptProduct;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

import static cash.service.ServiceForServ.getId_lang;
import static cash.service.ServiceForServ.isValidate;

@WebServlet("/merch/setAmountProduct")
public class ServletProductSetAmount extends HttpServlet {
    ProductDaoImpl productDao = new ProductDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = productDao.findAllByLang(getId_lang(request));
        request.getSession()
                .setAttribute("products", products);
        request.getRequestDispatcher("/WEB-INF/jsp/product/updateAmount.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isValidate(request)) {
            Product product = null;
            double amount = 0;
            if (request.getParameter("productNA") != null && (request.getParameter("amountNA") != null)) {
                product = productDao.findProductByNameLang(request.getParameter("productNA"), getId_lang(request));
                amount = Double.parseDouble(request.getParameter("amountNA"));
            } else if (request.getParameter("productCA") != null && request.getParameter("amountCA") != null) {
                product = productDao.findProductByCodeLang(request.getParameter("productCA"), getId_lang(request));

                amount = Double.parseDouble(request.getParameter("amountCA"));
                System.out.println(amount);
            }
            if (productDao.updateAmount(product, amount)) {
                request.getSession().setAttribute("message", "set new amount");
            } else {
                request.getSession().setAttribute("message", "did not set new amount");
            }
            response.sendRedirect("/ServletBack");
        } else {
            request.getSession().setAttribute("message", "not all fields are filled");
            request.getRequestDispatcher("/WEB-INF/jsp/product/updateAmount.jsp")
                    .forward(request, response);
        }
    }


}
