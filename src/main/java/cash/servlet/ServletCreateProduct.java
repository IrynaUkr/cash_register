package cash.servlet;

import cash.db.dao.impl.TransactionDAOImpl;
import cash.entity.Product;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/merch/createProduct")
public class ServletCreateProduct extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/product/createProduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isCreateFormValid(request)) {
            System.out.println("product valid");
            Product product = getProduct(request);
            if (new TransactionDAOImpl().createProductWithTranslate(product, getNames(request), getDescriptions(request))) {
                request.getSession().setAttribute("message", "product was added!");
                response.sendRedirect("/ServletBack");
            } else {
                request.getSession().setAttribute("message", "product could not be added");
                request.getRequestDispatcher("/WEB-INF/jsp/product/createProduct.jsp").forward(request, response);
            }
        } else {
            request.getSession().setAttribute("message", "not all fields are filled");
            request.getRequestDispatcher("/WEB-INF/jsp/product/createProduct.jsp").forward(request, response);
        }
    }


    private boolean isCreateFormValid(HttpServletRequest request) {
        System.out.println(request.getParameter("code"));
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

    private Product getProduct(HttpServletRequest request) {
        String code = request.getParameter("code");
        Double price = Double.valueOf((request.getParameter("price")));
        Double amount = Double.valueOf(request.getParameter("amount"));
        String uom = request.getParameter("uom");
        return new Product(code, price, amount, uom);
    }

    private HashMap<Integer, String> getDescriptions(HttpServletRequest request) {
        HashMap<Integer, String> descriptions = new HashMap<>();
        descriptions.put(1, request.getParameter("descriptionEn"));
        descriptions.put(2, request.getParameter("descriptionUa"));
        descriptions.put(3, request.getParameter("descriptionRu"));
        System.out.println("desc" + descriptions);
        return descriptions;
    }

    private HashMap<Integer, String> getNames(HttpServletRequest request) {
        HashMap<Integer, String> names = new HashMap<>();
        names.put(1, request.getParameter("nameEn"));
        names.put(2, request.getParameter("nameUa"));
        names.put(3, request.getParameter("nameRu"));
        System.out.println("names" + names);
        return names;
    }
}