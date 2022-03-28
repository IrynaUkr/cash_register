package cash.servlet;

import cash.entity.Role;
import cash.service.ServLetUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ServletBack", value = "/ServletBack")
public class ServletBack extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ServletBack.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Servlet: ServletBack. Method: Get");
        if (request.getSession().getAttribute("role") != "") {
            Role role = Role.valueOf(request.getSession().getAttribute("role").toString());
            ServLetUtils.chooseStartPage(role, request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
