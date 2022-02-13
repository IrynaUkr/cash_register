package cash.servlet;

import cash.entity.Role;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static cash.entity.Role.*;

@WebServlet(name = "ServletBack", value = "/ServletBack")
public class ServletBack extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Role role = Role.valueOf(request.getSession().getAttribute("role").toString());
        switch (role) {
            case ADMIN -> request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp").forward(request, response);
            case CASHIER -> request.getRequestDispatcher("/WEB-INF/jsp/cashier.jsp").forward(request, response);
            case MERCHANDISER -> request.getRequestDispatcher("/WEB-INF/jsp/startMerch.jsp").forward(request, response);
            case CHIEF_CASHIER -> request.getRequestDispatcher("/WEB-INF/jsp/startChief.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
