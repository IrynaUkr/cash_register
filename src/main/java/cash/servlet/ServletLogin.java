package cash.servlet;

import cash.db.dao.impl.UserDaoImpl;
import cash.entity.Role;
import cash.entity.User;
import cash.service.HashUtils;
import cash.service.ServLetUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Objects;

@WebServlet("/login")
public class ServletLogin extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ServletLogin.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Servlet: ServletLogin. Method: Get");
        response.sendRedirect("login.jsp");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Servlet: ServletLogin. Method: Post");
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        String login = request.getParameter("user");
        String userPassword = request.getParameter("password");
        User user = userDao.findEntityByLogin(login);
        try {
            if (!HashUtils.validatePassword(userPassword, user.getPassword())) {
                request.getSession().setAttribute("message", "Not allowed!");
                response.sendRedirect("login.jsp");
            } else {
                Role role = user.getRole();
                request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("role", user.getRole());
                ServLetUtils.chooseStartPage(role, request, response);
                System.out.println(login);
                System.out.println(userPassword);
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }
}


