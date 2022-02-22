package cash.servlet;

import cash.db.dao.impl.ProductDaoImpl;
import cash.entity.Product;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/merch/createProduct")
public class ServletCreateProduct extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/product/createProduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("code") != null
                && request.getParameter("price") != null
                && request.getParameter("amount") != null) {
            String code = request.getParameter("code");
            Double price = Double.valueOf((request.getParameter("price")));
            Double amount = Double.valueOf(request.getParameter("amount"));
            String uom = request.getParameter("uom");
            Product product = new Product(code, price, amount, uom);
            ProductDaoImpl productDao = new ProductDaoImpl();
            boolean flag = productDao.create(product);
            String nameEn = request.getParameter("nameEn");
            String nameRu = request.getParameter("nameRu");
            String nameUa = request.getParameter("nameUa");
            String descriptionEn = request.getParameter("descriptionEn");
            String descriptionRu = request.getParameter("descriptionRu");
            String descriptionUa = request.getParameter("descriptionUa");
            productDao.setNameDescription(product.getId(), 1, nameEn, descriptionEn);
            productDao.setNameDescription(product.getId(), 2, nameUa, descriptionUa);
            productDao.setNameDescription(product.getId(), 3, nameRu, descriptionRu);
            if (flag) {
                request.getSession().setAttribute("message", "product was added!");
                response.sendRedirect("/ServletBack");
            } else {
                request.getSession().setAttribute("message", "product was not added!");
                response.sendRedirect("/ServletBack");
            }
        }
    }
}