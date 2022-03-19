package cash.servlet;


import cash.db.dao.impl.UserDaoImpl;
import cash.entity.*;
import cash.service.ServLetUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/admin/users")
public class ServletCreateUsers extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (ServLetUtils.isCreateUserFormValid(request)) {
            User newUser = getUserFromRequest(request);
            UserDaoImpl userDao = UserDaoImpl.getInstatnce();
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

    private User getUserFromRequest(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String surname = request.getParameter("surname");
        Role role = Role.valueOf(request.getParameter("role"));
        String address = request.getParameter("address");
        String phoneNumber = request.getParameter("phone number");
        String email = request.getParameter("email");
        return new User(login, password, surname, phoneNumber, email, address, role);
    }

}
