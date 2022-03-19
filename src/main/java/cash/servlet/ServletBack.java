package cash.servlet;

import cash.entity.Role;
import cash.service.ServLetUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static cash.entity.Role.*;

@WebServlet(name = "ServletBack", value = "/ServletBack")
public class ServletBack extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("role") != "") {
            Role role = Role.valueOf(request.getSession().getAttribute("role").toString());
            ServLetUtils.chooseStartPage(role, request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
