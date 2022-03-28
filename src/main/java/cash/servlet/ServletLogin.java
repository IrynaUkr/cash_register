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
        if (ServLetUtils.isLoginFormValid(request)) {
            String login = request.getParameter("login");
            String userPassword = request.getParameter("password");
            User user = UserDaoImpl.getInstance().findEntityByLogin(login);
            if (user != null) {
                try {
                    if (!HashUtils.validatePassword(userPassword, user.getPassword())) {
                        request.getSession().setAttribute("message", "Not allowed!");
                        response.sendRedirect("login.jsp");
                    } else {
                        Role role = user.getRole();
                        request.getSession().setAttribute("user", user);
                        request.getSession().setAttribute("role", user.getRole());
                        ServLetUtils.chooseStartPage(role, request, response);
                        logger.info("user "+user+" logged in");
                    }
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    logger.error("Hashing password was not successful", e);
                }
            } else {
                logger.info("user with login" + login +" and password "+ userPassword+ " not found");
                request.getSession().setAttribute("message", "user with login " + login + " not found!");
                response.sendRedirect("login.jsp");
            }
        }else{
            request.getSession().setAttribute("message", "required fields are empty!");
            response.sendRedirect("login.jsp");
        }
    }
}


