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
        request.getRequestDispatcher("/WEB-INF/jsp/product/createProduct.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        Double price = Double.valueOf((request.getParameter("price")));
        Double amount = Double.valueOf(request.getParameter("amount"));
        String uom = request.getParameter("uom");
        Product product = new Product(code, name, price, amount, uom, description);
        ProductDaoImpl productDao = new ProductDaoImpl();
        boolean flag = productDao.create(product);
        if (flag){
            System.out.println("product was added: " + product);
            request.getSession().setAttribute("message", "product was added!");
            response.sendRedirect("/ServletBack");
        }else{
            request.getSession().setAttribute("message", "product was not added!");
            response.sendRedirect("/ServletBack");
        }
    }
}
