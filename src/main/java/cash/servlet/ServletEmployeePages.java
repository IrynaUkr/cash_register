package cash.servlet;

import cash.db.dao.impl.UserDaoImpl;
import cash.db.manager.DBManager;
import cash.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

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
        int totalAmountRecords;
        int totalAmPages;
        List<User> users;
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
            isUserDeleted = isUsersDeleted(request.getParameterValues("selected"));
        }
        if (isUserDeleted) {
            request.getSession().setAttribute("message", "users were deleted!");
            response.sendRedirect("/ServletBack");
        } else {
            request.getSession().setAttribute("message", "user could not be deleted, he created documents ");
            response.sendRedirect("/ServletBack");
        }
    }

    private boolean isUsersDeleted(String[] loginList) {
        boolean isUserDeleted = false;
        for (String login : loginList) {
            isUserDeleted= userDao.deleteWithConnection(login, connection);
        }
        return isUserDeleted;
    }
}
