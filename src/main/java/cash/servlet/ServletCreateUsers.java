package cash.servlet;


import cash.db.dao.impl.UserDaoImpl;
import cash.entity.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/admin/users")
public class ServletCreateUsers extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doGet from users");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        System.out.println(login+"from the form");
        String password = request.getParameter("password");
        String surname = request.getParameter("surname");
        Role role = Role.valueOf(request.getParameter("role"));
        String address = request.getParameter("address");
        String phoneNumber = request.getParameter("phone number");
        String email = request.getParameter("email");

        User newUser = new User(login, password,surname,role);
        UserDaoImpl userDao = new UserDaoImpl();
       if( userDao.create(newUser)){
           System.out.println("user was added: " + newUser);
           request.getSession().setAttribute("message", "user was added!");
           response.sendRedirect("/ServletBack");
       }else {
           request.getSession().setAttribute("message", "user was not added!");
           response.sendRedirect("/ServletBack");
       }
    }
}
