package cash.servlet;

import cash.db.dao.impl.UserDaoImpl;
import cash.entity.Role;
import cash.entity.User;
import cash.service.ServLetUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/login")
public class ServletLogin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("login.jsp");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDaoImpl userDao = UserDaoImpl.getInstatnce();
        String login = request.getParameter("user");
        String userPassword = request.getParameter("password");
        User user = userDao.findEntityByLogin(login);
        System.out.println(user);
        if (user == null || !(user.getPassword().equals(userPassword))) {
            request.getSession().setAttribute("message", "Not allowed!");
            response.sendRedirect("login.jsp");
        } else {
            Role role = user.getRole();
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("role", user.getRole());
            ServLetUtils.chooseStartPage(role,request,response);
            System.out.println(login);
            System.out.println(userPassword);

        }
    }

}
