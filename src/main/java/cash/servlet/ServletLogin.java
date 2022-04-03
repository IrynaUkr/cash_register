package cash.servlet;

import cash.entity.Role;
import cash.entity.User;
import cash.service.HashUtils;
import cash.service.ServLetUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
            User user = ServLetUtils.getUserFromRequest(request);
            if (user != null) {
                try {
                    if (!HashUtils.validatePassword(request.getParameter("password"), user.getPassword())) {
                        request.getSession().setAttribute("message", "Not allowed!");
                        response.sendRedirect("login.jsp");
                    } else {
                        Role role = user.getRole();
                        request.getSession().setAttribute("user", user);
                        request.getSession().setAttribute("role", user.getRole());
                        ServLetUtils.chooseStartPage(role, request, response);
                        logger.info("user " + user + " logged in");
                    }
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    logger.error("Hashing password was not successful", e);
                }
            } else {
                logger.info("user with  not found");
                request.getSession().setAttribute("message", "user not found!");
                response.sendRedirect("login.jsp");
            }
        } else {
            request.getSession().setAttribute("message", "required fields are empty!");
            response.sendRedirect("login.jsp");
        }
    }
}


