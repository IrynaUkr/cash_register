package cash.servlet;


import cash.db.dao.impl.UserDaoImpl;
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

@WebServlet("/admin/users")
public class ServletCreateUsers extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ServletCreateUsers.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Servlet: ServletCreateUsers. Method: Post");
        if (ServLetUtils.isCreateUserFormValid(request)) {
            User newUser = null;
            try {
                newUser = getUserFromRequest(request);
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                e.printStackTrace();
            }
            UserDaoImpl userDao = UserDaoImpl.getInstance();
            if (userDao.create(newUser)) {
                System.out.println("user was added: " + newUser);
                request.getSession().setAttribute("message", "user was added!");
                response.sendRedirect("/ServletBack");
            }
        } else {
            request.getSession().setAttribute("message", "user was not added!");
            response.sendRedirect("/ServletBack");
        }
    }

    private User getUserFromRequest(HttpServletRequest request) throws NoSuchAlgorithmException, InvalidKeySpecException {
        logger.info("Servlet: ServletCreateUsers. Method: getUserFromRequest");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String surname = request.getParameter("surname");
        Role role = Role.valueOf(request.getParameter("role"));
        String address = request.getParameter("address");
        String phoneNumber = request.getParameter("phone");
        String email = request.getParameter("email");
        String generatedSecuredPasswordHash
                = HashUtils.generateStrongPasswordHash(password);
        return new User(login, generatedSecuredPasswordHash, surname, phoneNumber, email, address, role);
    }
}
