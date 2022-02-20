package cash.servlet;

import cash.db.dao.impl.ProductDaoImpl;
import cash.entity.Product;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/merch/ServletProductPages")
public class ServletProductPages extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = 3;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        ProductDaoImpl productDao = new ProductDaoImpl();
        List<Product> products = productDao.viewAllWithRestrict((page - 1) * recordsPerPage, recordsPerPage);
        int totalAmountRecords = productDao.getTotalAmountRecords();
        int totalAmPages = (int) Math.ceil(totalAmountRecords * 1.0 / recordsPerPage);
               request.getSession().setAttribute("products", products);
        request.getSession().setAttribute("currentPage",page);
        System.out.println(page +"page");
        request.getSession().setAttribute("totalAmPages",totalAmPages);
        System.out.println(totalAmPages+"totalAmPages" );
        request.getRequestDispatcher("/WEB-INF/jsp/product/displayProducts.jsp").forward(request, response);
        System.out.println(products);

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
