package cash.servlet;

import cash.db.dao.impl.ProductDaoImpl;
import cash.entity.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static cash.service.ServLetUtils.getIdLang;
import static cash.service.ServLetUtils.isNameAndAmountValid;

@WebServlet("/merch/setAmountProduct")
public class ServletProductSetAmount extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ServletBack.class);
    ProductDaoImpl productDao = ProductDaoImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Servlet: ServletProductSetAmount. Method: Get");
        List<Product> products = productDao.findAllByLang(getIdLang(request));
        request.getSession()
                .setAttribute("products", products);
        request.getRequestDispatcher("/WEB-INF/jsp/product/updateAmount.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Servlet: ServletProductSetAmount. Method: Post");
        if (isNameAndAmountValid(request)) {
            Product product = null;
            double amount = 0;
            if (request.getParameter("productNA") != null && (request.getParameter("amountNA") != null)) {
                product = productDao.findProductByNameLang(request.getParameter("productNA"), getIdLang(request));
                amount = Double.parseDouble(request.getParameter("amountNA"));
            } else if (request.getParameter("productCA") != null && request.getParameter("amountCA") != null) {
                product = productDao.findProductByCodeLang(request.getParameter("productCA"), getIdLang(request));
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
