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


@WebServlet("/merch/ServletProductPages")
public class ServletProductPages extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ServletProductPages.class);
    ProductDaoImpl productDao = ProductDaoImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Servlet: ServletProductPages. Method: Get");
        int id_lang = getIdLang(request);
        int page = 1;
        int recordsPerPage = 4;
        int totalAmountRecords = 0;
        int totalAmPages = 0;
        List<Product> products = null;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        if (request.getParameter("ProductCode") != null) {
            request.getSession().setAttribute("sorting", "code");
        } else if (request.getParameter("ProductName") != null) {
            request.getSession().setAttribute("sorting", "name_tr");
        } else if (request.getParameter("ProductPrice") != null) {
            request.getSession().setAttribute("sorting", "price");
        } else if (request.getParameter("ProductAmount") != null) {
            request.getSession().setAttribute("sorting", "amount");
        } else if (request.getParameter("ProductUOM") != null) {
            request.getSession().setAttribute("sorting", "uom");
        }
        if (request.getSession().getAttribute("sorting") != null) {
            String sorting = String.valueOf(request.getSession().getAttribute("sorting"));
            products = productDao.viewAllWithSorting((page - 1) * recordsPerPage, recordsPerPage, sorting, id_lang);
        } else {
            products = productDao.findAllWithRestrict((page - 1) * recordsPerPage, recordsPerPage, id_lang);
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
        logger.info("Servlet: ServletProductPages. Method: Post");
        boolean isProductDeleted = false;
        if (request.getParameterValues("selected") != null) {
            String[] codes = request.getParameterValues("selected");
            isProductDeleted = productDao.deleteProductsByCode(codes);
        }
        if (isProductDeleted) {
            request.getSession().setAttribute("message", "products were deleted!");
            response.sendRedirect("/ServletBack");
        } else {
            request.getSession().setAttribute("message", "products were not deleted, they are in receipts! ");
            response.sendRedirect("/ServletBack");
        }
    }
}

