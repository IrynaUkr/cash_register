package cash.servlet;

import cash.db.dao.impl.TransactionDAOImpl;
import cash.entity.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/merch/createProduct")
public class ServletCreateProduct extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ServletCreateProduct.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Servlet: merch/createProduct. Method: Get");
        request.getRequestDispatcher("/WEB-INF/jsp/product/createProduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Servlet: merch/createProduct. Method: Post");
        if (isCreateProductFormValid(request)) {
            Product product = getProductFromRequest(request);
            if (TransactionDAOImpl.getInstance().createProductWithTranslate(product, getNames(request), getDescriptions(request))) {
                request.getSession().setAttribute("message", "product was added!");
                response.sendRedirect("/ServletBack");
            } else {
                request.getSession().setAttribute("message", "product could not be added");
                request.getRequestDispatcher("/WEB-INF/jsp/product/createProduct.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("message", "required fields are empty");
            request.getRequestDispatcher("/WEB-INF/jsp/product/createProduct.jsp").forward(request, response);
        }
    }


    private boolean isCreateProductFormValid(HttpServletRequest request) {
        logger.info("Servlet: merch/createProduct. Method: isCreateProductFormValid");
        return (request.getParameter("code") != ""
                && request.getParameter("price") != ""
                && request.getParameter("amount") != ""
                && request.getParameter("uom") != ""
                && request.getParameter("nameEn") != ""
                && request.getParameter("nameRu") != ""
                && request.getParameter("nameUa") != ""
                && request.getParameter("descriptionEn") != ""
                && request.getParameter("descriptionRu") != "");
        }

    private Product getProductFromRequest(HttpServletRequest request) {
        logger.info("Servlet: merch/createProduct. Method: getProductFromRequest");
        String code = request.getParameter("code");
        Double price = Double.valueOf((request.getParameter("price")));
        Double amount = Double.valueOf(request.getParameter("amount"));
        String uom = request.getParameter("uom");
        return new Product(code, price, amount, uom);
    }

    private HashMap<Integer, String> getDescriptions(HttpServletRequest request) {
        logger.info("Servlet: merch/createProduct. Method: getDescriptions with translate");
        HashMap<Integer, String> descriptions = new HashMap<>();
        descriptions.put(1, request.getParameter("descriptionEn"));
        descriptions.put(2, request.getParameter("descriptionUa"));
        descriptions.put(3, request.getParameter("descriptionRu"));
        System.out.println("desc" + descriptions);
        return descriptions;
    }

    private HashMap<Integer, String> getNames(HttpServletRequest request) {
        logger.info("Servlet: merch/createProduct. Method: getNames with translate");
        HashMap<Integer, String> names = new HashMap<>();
        names.put(1, request.getParameter("nameEn"));
        names.put(2, request.getParameter("nameUa"));
        names.put(3, request.getParameter("nameRu"));
        System.out.println("names" + names);
        return names;
    }
}