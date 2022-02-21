package cash.servlet;

import cash.db.dao.impl.ProductDaoImpl;
import cash.entity.Product;
import cash.entity.Receipt;
import cash.service.ServiceReceiptProduct;
import jdk.jfr.Frequency;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static cash.service.ServiceReceiptProduct.*;

@WebServlet("/merch/ServletProductPages")
public class ServletProductPages extends HttpServlet {
    ProductDaoImpl productDao = new ProductDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = 5;
        int totalAmountRecords = 0;
        int totalAmPages = 0;
        List<Product> products = null;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        if (request.getParameter("ProductCode") != null) {
            request.getSession().setAttribute("sorting", "code");
        } else if (request.getParameter("ProductName") != null) {
            request.getSession().setAttribute("sorting", "name");
        } else if (request.getParameter("ProductPrice") != null) {
            request.getSession().setAttribute("sorting", "price");
        } else if (request.getParameter("ProductAmount") != null) {
            request.getSession().setAttribute("sorting", "amount");
        } else if (request.getParameter("ProductUOM") != null) {
            request.getSession().setAttribute("sorting", "uom");
        }
        if (request.getSession().getAttribute("sorting") != null) {
            String sorting = String.valueOf(request.getSession().getAttribute("sorting"));
            products = productDao.viewAllWithSorting((page - 1) * recordsPerPage, recordsPerPage, sorting);
        } else {
            products = productDao.viewAllWithRestrict((page - 1) * recordsPerPage, recordsPerPage);
        }
        totalAmountRecords = productDao.getTotalAmountRecords();
        totalAmPages = (int) Math.ceil(totalAmountRecords * 1.0 / recordsPerPage);
        request.getSession().setAttribute("products", products);
        request.getSession().setAttribute("currentPage", page);
        request.getSession().setAttribute("totalAmPages", totalAmPages);
        request.getRequestDispatcher("/WEB-INF/jsp/product/displayProducts.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean flag = false;
        if (request.getParameterValues("selected") != null) {
            String[] codes = request.getParameterValues("selected");
            flag = productDao.deleteByCode(codes);
        }
        if (flag) {
            request.getSession().setAttribute("message", "products was deleted!");
            response.sendRedirect("/ServletBack");
        } else {
            request.getSession().setAttribute("message", "products was not deleted, they are in receipts! ");
            response.sendRedirect("/ServletBack");
        }
    }
}

