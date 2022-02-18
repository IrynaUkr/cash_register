package cash.servlet;

import cash.db.dao.impl.ProductDaoImpl;
import cash.service.Transaction;
import cash.entity.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/cashier/createReceipt")
public class ServletSaveReceiptToDB extends HttpServlet {
    ProductDaoImpl productDao = new ProductDaoImpl();
    Transaction transaction = new Transaction();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = productDao.findAll();
        System.out.println(products);
        request.getSession().setAttribute("products", products);
        request.getRequestDispatcher("/WEB-INF/jsp/receipt/createReceipt.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Receipt receipt = (Receipt) request.getSession().getAttribute("receipt");
        try {
            transaction.saveReceiptToDB(receipt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/ServletBack");
    }


}
