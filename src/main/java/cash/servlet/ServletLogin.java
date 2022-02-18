package cash.servlet;

import cash.db.dao.impl.UserDaoImpl;
import cash.entity.Role;
import cash.entity.User;

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
        UserDaoImpl userDao = new UserDaoImpl();
        String user = request.getParameter("user");
        String userPassword = request.getParameter("password");
        User entityByLogin = userDao.findEntityByLogin(user);
        System.out.println(entityByLogin);
        if (entityByLogin == null || !(entityByLogin.getPassword().equals(userPassword))) {
            request.getSession().setAttribute("message", "Not allowed!");
            response.sendRedirect("login.jsp");
        } else {
            Role role = entityByLogin.getRole();
            request.getSession().setAttribute("user", entityByLogin);
            request.getSession().setAttribute("role", entityByLogin.getRole());
            switch (role) {
                case ADMIN -> request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp").forward(request,response);
                case CASHIER -> request.getRequestDispatcher("/WEB-INF/jsp/cashier.jsp").forward(request,response);
                case MERCHANDISER -> request.getRequestDispatcher("/WEB-INF/jsp/startMerch.jsp").forward(request,response);
                case CHIEF_CASHIER -> request.getRequestDispatcher("/WEB-INF/jsp/startChief.jsp").forward(request,response);
            }
            System.out.println(user);
            System.out.println(userPassword);

        }
    }
}
