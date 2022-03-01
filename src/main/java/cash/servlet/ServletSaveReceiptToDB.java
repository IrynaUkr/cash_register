package cash.servlet;

import cash.db.dao.ProductDao;
import cash.db.dao.TransactionDao;
import cash.db.dao.impl.ProductDaoImpl;
import cash.db.dao.impl.TransactionDAOImpl;
import cash.entity.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

import static cash.service.ServiceForServ.getId_lang;

@WebServlet("/cashier/createReceipt")
public class ServletSaveReceiptToDB extends HttpServlet {
    ProductDao productDao = new ProductDaoImpl();
    TransactionDao transactionDao = new TransactionDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id_lang = getId_lang(request);
        List<Product> products = productDao.findAllByLang(id_lang);
        request.getSession().setAttribute("products", products);
        request.getRequestDispatcher("/WEB-INF/jsp/receipt/createReceipt.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Receipt receipt = (Receipt) request.getSession().getAttribute("receipt");
        transactionDao.saveReceiptToDB(receipt);
        response.sendRedirect("/ServletBack");
    }


}
