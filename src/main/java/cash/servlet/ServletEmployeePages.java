package cash.servlet;

import cash.db.dao.impl.ProductDaoImpl;
import cash.db.dao.impl.UserDaoImpl;
import cash.db.manager.DBManager;
import cash.entity.Product;
import cash.entity.User;
import cash.service.ServLetUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import static cash.service.ServLetUtils.getIdLang;

@WebServlet("/admin/servletEmployeePages")
public class ServletEmployeePages extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ServletEmployeePages.class);
    UserDaoImpl userDao = UserDaoImpl.getInstance();
    Connection connection;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Servlet: ServletEmployeePages. Method: Get");
        int page = 1;
        int recordsPerPage = 4;
        int totalAmountRecords = 0;
        int totalAmPages = 0;
        List<User> users = null;
        connection = DBManager.getInstance().getConnection();
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        if (request.getParameter("login") != null) {
            request.getSession().setAttribute("sorting", "login");
        } else if (request.getParameter("role") != null) {
            request.getSession().setAttribute("sorting", "role");
        } else if (request.getParameter("surname") != null) {
            request.getSession().setAttribute("sorting", "surname");
        }
        if (request.getSession().getAttribute("sorting") != null) {
            String sorting = String.valueOf(request.getSession().getAttribute("sorting"));

            users = userDao.viewAllWithSortingWithCon((page - 1) * recordsPerPage, recordsPerPage, sorting, connection);
        } else {
            users = userDao.findAllWithRestrictWithCon((page - 1) * recordsPerPage, recordsPerPage, connection);
        }
        totalAmountRecords = userDao.getTotalAmountRecords();
        totalAmPages = (int) Math.ceil(totalAmountRecords * 1.0 / recordsPerPage);
        request.getSession().setAttribute("employees", users);
        request.getSession().setAttribute("currentPage", page);
        request.getSession().setAttribute("totalAmPages", totalAmPages);
        request.getRequestDispatcher("/WEB-INF/jsp/employee/showEmployee.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Servlet: ServletEmployeePages. Method: Post");
        boolean isUserDeleted = false;
        if (request.getParameterValues("selected") != null) {
            String[] loginList = request.getParameterValues("selected");
            for(String login: loginList){
                isUserDeleted =userDao.deleteWithConnection(login,connection);
            }
        }
        if (isUserDeleted) {
            request.getSession().setAttribute("message", "users were deleted!");
            response.sendRedirect("/ServletBack");
        } else {
            request.getSession().setAttribute("message", "users were not deleted ");
            response.sendRedirect("/ServletBack");
        }
    }
}
